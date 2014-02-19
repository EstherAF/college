
%{
#include <math.h>
#include <stdio.h>
#include "ts.h"
#include "errores.h"
	/*
	int numero_linea;
	void yyerror(char *)
	void error_identificado(int, char*)
	void warning(int, char*)
	*/
%}

%union {
	double	numero; 	//Valor numérico. Es el tipo de dato del símbolo terminal _NUM (número float o integer), y del no terminal exp (que propaga el valor numérico de las operaciones realizadas)
	lexema * slex;		//Estructura especificada en ts.h, es uno de los componentes de los nodos de la tabla de símbolos. Es el tipo de dato de los identificadores o variables, constantes y funciones
}


/*Simbolos terminales*/
%token <numero> _NUM
%token <slex> _ID _FNCT _CONST

/*Símbolos no terminales. No se espeicifica el tipo de dato de los demás no terminales ya que no se accede unca a su valor su semántico.*/
%type <numero> exp


/*Especificación de la asociatividad de los operadores(por la derecha o por la izquierda)
Verticalmente, de menor a mayor, la preferencia entre ellos.
*/
%right '='	
%left '+' '-'
%left '*' '/'
%right '^'


%%
/*GRAMÁTICA*/
input:	/*Nodo raiz del árbol de derivación, que se extiende en las líneas a ejecutar */
		| input line
		;

line:	/*Desde line, si se ha detectado algún error en los niveles inferiores del árbol de derivación, no se propaga el error al nodo raíz, para poder continuar la ejecución de las siguientes líneas*/

	/*Cuando se lee un salto de línea se actualiza la variable global que almacena el número de línea que se está compilando en cada momento*/
	'\n'	{ numero_linea++;}


	/*Cuando una línea termina en salto de línea, sin ';', se muestra el resutado de su ejecución*/
	|exp '\n' { 
			printf("%d:\t%lf\n",numero_linea, $1);	
			numero_linea++; //Se actualiza el numero de línea
		 }

	 /*Cuando una línea termina en ';', no se muestra el resutado de su ejecución*/ 
	|exp ';' {}

	/*Al producirse error en cualquier momento del análisis sintáctico de una línea, esa línea no se ejecuta, pero se continúa con la compilación de las siguientes*/
	| error ';' { }
	| error '\n' { }
;


exp:	
	/*Símbolo terminales: número (puede ser un integer o float), constante e identificador o variable (definidas en la tabla de símbolos)*/
	_NUM	{$$ = $1;}
	| _CONST 	{ $$ = $1->contenido.valor; }
	| _ID		{ 
				/*Se detecta el error de que la variable contenga un valor no numérico (nan), como resultado (generalmente) de ejecutar una operación aritmética de un valor no perteneciente a su dominio.*/
				if(isnan($1->contenido.valor)){
					error_identificado(_NAN,$1->nombre);
					YYERROR;
				}else
					$$ = $1->contenido.valor; 
			}

	/*Sentencia de asignación, como término de la izquierda tiene que ser una varibale, y como término de la derecha un valor numérico*/
	| _ID '=' exp {
				$$=$3;
				$1->contenido.valor=$3;
			}

	/*
	Llamada a una función. Sólo están implementadas funciones que reciben un único parámetro de valor numérico.
	*/
	| _FNCT '(' exp ')' { 
				//se muestran warnings cuando se intenta aplicar una función a un número que no pertenece a su dominio

				//Warning cuando se intenta calcular el logaritmo de un número menor o igual que 0
				if((strcmp($1->nombre,"log")==0 | strcmp($1->nombre,"log10")==0) && $3<=0)
					warning(_LOGARITMO);

				//Warning cuando se intenta calcular la tangente de valores como ...,-5*pi/2, -3*pi/2, -pi/2, pi/2, 3*pi/2, 5*pi/2, ...
				else if( strcmp($1->nombre,"tan")==0 ){
					double parte_entera;
					double calculo=$3/M_PI+0.5;
					if(modf ( calculo, &parte_entera)==0){
						warning(_TANGENTE);
					}
				}

				//Warning cuando se intenta calcular el arco seno o el arco coseno de un valor menor que -1 o mayor que 1
				else if( (strcmp($1->nombre,"asin")==0) && ($3>1 || $3<-1)){
					warning(_ARCOSENO);
				}
				else if( (strcmp($1->nombre,"acos")==0) && ($3>1 || $3<-1)){
					warning(_ARCOCOSENO);
				}	

				//Aunque se muestre el warning, el cálculo se realiza			
				$$=(*($1->contenido.fnct))($3); 
			}


	/*
	Operaciones aritméticas básicas 
	*/
	| exp '+' exp { $$=$1+$3; }
	| exp '-' exp { $$=$1-$3; }
	| exp '*' exp { $$=$1*$3; }
	| exp '/' exp { 
				if($3==0) warning(_DENOMINADOR_CERO);
				$$=$1/$3;
			}
	| exp '^' exp { $$=pow($1,$3); }

	/*
	Alteración de la preferencia con paréntesis
	*/
	| '(' exp ')' { $$=$2; }

	/*
	Negación de un valor (*-1) 
	*/
	| '-' exp { $$=-$2; }


/*Detección de errores*/
/*Se muestra y se propagada el error con YYERROR a niveles superiores, para evitar que la línea se ejecute. */
	
	/*Errores en las sentencias de las operaciones básicas*/
	| exp '+' error { error_identificado(_OPERANDO_ARITMETICO,"+"); YYERROR;}
	| '+' exp { error_identificado(_OPERANDO_ARITMETICO,"+"); YYERROR;}

	| exp '-' error { error_identificado(_OPERANDO_ARITMETICO,"-"); YYERROR;}

	| exp '*' error { error_identificado(_OPERANDO_ARITMETICO,"*"); YYERROR;}
	| '*' exp { error_identificado(_OPERANDO_ARITMETICO,"*");	YYERROR; }

	| exp '/' error { error_identificado(_OPERANDO_ARITMETICO,"/");	 YYERROR;}
	| '/' exp { error_identificado(_OPERANDO_ARITMETICO,"/"); YYERROR;}

	| exp '^' error { error_identificado(_OPERANDO_ARITMETICO,"^");	 YYERROR;}
	| '^' exp { error_identificado(_OPERANDO_ARITMETICO,"^"); YYERROR;}

	/*Error de paréntesis vacíos*/
	| '('')' { error_identificado(_PARENTESIS_VACIO,NULL); YYERROR;}

	/*Error de asignaciones incorrectas (asignación a algo distinto de una variable)*/
	| _CONST '=' exp{ 
				/*Detecta que el término de la izquierda de una asignación no es válido (función o constante) o inexistente*/
				error_identificado(_ASIGNACION_INCORRECTA,$1->nombre);
				YYERROR;
			}

	| _NUM '=' exp { 
				/*Detecta que el término de la izquierda de una asignación no es válido (función o constante) o inexistente*/
				char elemento[20];
				sprintf(elemento,"%f",$1);
				error_identificado(_ASIGNACION_INCORRECTA,elemento);
				YYERROR;
			}

	/*Error al como una función algo que no está declarado como tal (variables o constantes)*/
	| _ID '(' error ')' {
				error_identificado(_FUNCION_INVALIDA,$1->nombre);
				YYERROR;
			}

	| _CONST '(' error ')' {
				error_identificado(_FUNCION_INVALIDA,$1->nombre);
				YYERROR;
			}

	/*Error la forma de uso de una función*/
	| _FNCT error { 
				error_identificado(_PARAMETROS_FUNCION,$1->nombre);
				YYERROR;
			}

	;



