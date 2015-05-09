
%{	
	#include <string.h>
	#include <stdlib.h>
	#include <stdio.h>
	#include "ts.h"
	#include "bison.tab.h"
	#include "errores.h"

%}

ENTERO		[0-9]+		
REAL		([0-9])+"."([0-9])+
REAL_CIENTIFICO ([0-9])+"E""-"?([0-9])+
ID		[a-z][a-z0-9]*
BLANCOS		[ \t]*
SALTO_LINEA	[\n]

%%


{BLANCOS}	/*Se saltan las tabulaciones y los espacios*/

{SALTO_LINEA}	{	/*Reconoce saltos de línea, para el tratamiento de errores*/
					return yytext[0];
				}

{ID}	{
			/*Reconoce cadenas lésicoas, que pueden ser variables, constantes o funciones*/
			lexema *slex=(ts_encuentra_o_inserta(yytext));	//Se consulta a la tabla de símbolos
			yylval.slex=slex;
			return slex->comp_lexico;
		}


{REAL}	{
			/*Reconoce todos los números reales*/
			yylval.numero=atof(yytext);
			return _NUM;
		}

{REAL}"E""-"?{ENTERO} {
			/*Reconoce todos los números reales en notación científica*/
			yylval.numero=atof(yytext);
			return _NUM;
		}

{ENTERO} {
			/*Reconoce números enteros*/
			yylval.numero=atof(yytext);
			return _NUM;
		}
					
<<EOF>>	{	
			/*Reconoce el caracter de fin de fichero*/
			return 0;	
		}

"/*"	{	/*Reconoce comentarios, con la misma sentencia que c*/
		int c,num_saltos=0;
		while(1){
			while ( (c = input())!='*' && c != EOF){ /* elimina el texto del comentario */
				if(c=='\n')	
					num_saltos++;
			}
			if ( c == '*' ){
				while ( (c = input())=='*' );
				if ( c == '/' ){
					numero_linea+=num_saltos;	//Se retrasa el incrementeo del número de línea, para, si hay un error de comentario abierto, poder mostrar la línea en la que comienza
					break;		/* encontró el final */
				}
			}
			if ( c == EOF ){
				error_identificado(_COMENTARIO_NO_CERRADO,0);
				break;
			}
		}
	}



"("|")"|";"|"*"|"+"|"="|"/"|"^"|"-" {/*Reconoce cualquier otro carácter válido, no reconocido por las expresiones formales anteriores*/

					return yytext[0];
				}

.		{/*Error de caracter no reconocido*/
			error_identificado(_CARACTER_DESCONOCIDO,yytext);
		}



%%

void se_inicializar(char * archivo){
	yyin = fopen( archivo, "r" );
	numero_linea=0;
	if(yyin==NULL){			//Si el archivo no se puede abrir...
		error_identificado(_NOMBRE_ARCHIVO,archivo);
	}
	numero_linea++;
	printf("Línea\tResultado\t\n");
	
}
