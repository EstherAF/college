#include "lex.yy.h"
#include "ts.h"
#include "bison.tab.h"


int main(int argc, char *argv[]){
	se_inicializar(argv[1]);	//Se proporciona al sistema de entrada el nombre del archivo.	
	ts_inicializar();		//Inicialización de la tabla de símbolos
	yyparse();			//Llamada a la función del analizador sintáctco
	ts_mostrar_lista_variables();	//Se muestra la lista de variables y su valor
	//ts_mostrar_lista_completa();	//Se muestra la tabla de símbolos completa
}

