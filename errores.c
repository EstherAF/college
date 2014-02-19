#include "errores.h"
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>

int numero_linea;	//Uso de la variable global




/*FUNCIONES PÚBLICAS*/

/* 
Llamada por yyparse ante un error 
*/
void yyerror (char *s){ 
	if(strcmp("syntax error",s)==0)
		error_identificado(_LINEA_NO_EJECUTADA,NULL);
	else
		printf ("ERROR Línea %d: %s\n", numero_linea, s);
}


/*
Muestra mensajes predefinidos de error.
*/
void error_identificado(int identificador_error,char * elemento_erroneo){

	switch(identificador_error){
		
		case _RESERVA_MEMORIA:
			printf("ERROR: fallo al reservar memoria para el compilador. Abortando ejecución.\n");
			exit(1);	//Se aborta la ejecución
			break;

		case _NOMBRE_ARCHIVO:
			printf("ERROR: no se puede abrir el archivo \"%s\". Abortando ejecución.\n",elemento_erroneo);
			exit(-1); 	//Se aborta la ejecución
			break;

		case _CARACTER_DESCONOCIDO:
			printf("ERROR Línea %d: Caracter '' no reconocido.\n",numero_linea,elemento_erroneo);
			break;

		case _COMENTARIO_NO_CERRADO:
			printf("ERROR Línea %d: No se encontró el final del comentario.\n",numero_linea);
			exit(1);	//Se aborta la ejecución
			break;

		case _OPERANDO_ARITMETICO:
			printf("ERROR Línea %d: Falta uno de los operandos de la operación '%s'.\n",numero_linea,elemento_erroneo);
			break;

		case _PARENTESIS_VACIO:
			printf("ERROR Línea %d: Se espera un valor numérico entre '(' y ')'.\n",numero_linea);
			break;

		case _ASIGNACION_INCORRECTA:
			printf("ERROR Línea %d: El término de la izquierda de la asignación '%s' no es válido. Debería ser una variable.\n",numero_linea,elemento_erroneo);
			break;

		case _PARAMETROS_FUNCION:
			printf("ERROR Línea %d: Uso incorrecto de la función '%s'. Usar  de la forma '%s(numero)' dónde 'numero' es un único valor numérico.\n",numero_linea,elemento_erroneo,elemento_erroneo);
			break;

		case _FUNCION_INVALIDA:
			printf("ERROR Línea %d: No existe la función '%s'.\n",numero_linea,elemento_erroneo);
			break;

		case _LINEA_NO_EJECUTADA:
			printf("ERROR Línea %d: Error sintáctico, la línea no se ha podido ejecutar.\n",numero_linea);
			break;

		case _NAN:
			printf("ERROR Línea %d: La variable '%s' no contiene un número.\n",numero_linea,elemento_erroneo);
			break;

		default:
			printf("ERROR Línea %d: no identificado. Abortando ejecución.\n",numero_linea);
			exit(1);
	}
}


/*
Muestra mensajes predefinidos de warning.
*/
void warning (int identificador_warning){

	switch(identificador_warning){

		case _DENOMINADOR_CERO:
			printf("WARNING Línea %d: División con denominador 0.\n",numero_linea);
			break;

		case _LOGARITMO:
			printf("WARNING Línea %d: Logaritmo de un número menor o igual a 0.\n",numero_linea);
			break;

		case _TANGENTE:
			printf("WARNING Línea %d: La tangente de este valor puede no ser un número.\n",numero_linea);
			break;

		case _ARCOSENO:
			printf("WARNING Línea %d: El arco seno de este valor puede o ser un número.\n",numero_linea);
			break;

		case _ARCOCOSENO:
			printf("WARNING Línea %d: El arco coseno de este valor puede no es un número.\n",numero_linea);
			break;

		default:
			printf("WARNING Línea %d: warning no identificado.\n",numero_linea);

	}

}










