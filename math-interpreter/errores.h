/*DEFINICION DE LOS TIPOS DE ERROR*/

//Errores
#define _RESERVA_MEMORIA -1
#define _NOMBRE_ARCHIVO -2

#define _CARACTER_DESCONOCIDO -6
#define _COMENTARIO_NO_CERRADO -7

#define _LINEA_NO_EJECUTADA -10
#define _OPERANDO_ARITMETICO -11
#define _PARENTESIS_VACIO -12
#define _ASIGNACION_INCORRECTA -13
#define _PARAMETROS_FUNCION -14
#define _FUNCION_INVALIDA -15
#define _NAN -25

//Warnings
#define _DENOMINADOR_CERO -20
#define _LOGARITMO -21
#define _TANGENTE -22
#define _ARCOSENO -23
#define _ARCOCOSENO -24





/*
Variable externa global, que almacena el número de línea que se está compilando en cada momento. Útil para mostrar la localización de errores.
Se inicializa en la funcion se_inicializar del analizador léxico (flex.l) y se actualiza en el analizador sintáctico (bison.y)
*/
extern int numero_linea;




/*FUNCIONES PÚBLICAS*/

/*
Lo necesita bison en su análisis sintáctico.
*/
void yyerror (char *s);

/*
Se usa para mostrar mensajes predefinidos de errores identificados. 
Recibe por parámetro el código de error y un string con elemento erróneo. 
En los casos de _RESERVA_MEMORIA, _COMENTARIO_NO_CERRADO, _LINEA_NO_EJECUTADA y _PARENTESIS_VACIO el elemento erróneo no se usa, así que su valor es indiferente.
En los casos de _RESERVA_MEMORIA, _COMENTARIO_NO_CERRADO y _NOMBRE_ARCHIVO, tras mostrar el error, se aborta la ejecución.
Si recibe un código de error no identificado, muestra un error genérico y aborta la ejecución.
*/
void error_identificado(int identificador_error,char * elemento_erroneo);

/*
Se usa para mostrar warnings identificados. Recibe por parámetro el código del warning. 
Si recibe un código de warning no identificado, muestra un warning genérico.
*/
void warning (int identificador_warning);








