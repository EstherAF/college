#ifndef TS_H
#define TS_H


	typedef struct lex{ //estructura del lexema
		char * nombre;
		int comp_lexico;	//si es variable o funcion
		union{
			double valor; 		//contenido de la variable
			double (*fnct)();	//función		
		}contenido;
	}lexema;

	typedef struct nodo{   //elemento de la tabla
		lexema lexema;
		struct nodo *siguiente;
		struct nodo *anterior;
	}ts_nodo;



	/*Tanto la inserción como la búsqueda tienen complejidad log(n), ya que en ambos casos se utiliza el algoritmo de búsqueda binaria*/

	/*Búsqueda binaria o dicotómica:
	Reduce exponencialmente el número de iteraciones necesarias, respecto la búsqueda secuencial. Complejidad log(n)
	Consiste en seleccionar el conjunto de elementos en los que puede estar el elemento a buscar (inicialmente es el conjunto total), y compararlo con el elemento	central del conjunto. En función del resultado, se puede descartar una de las dos mitades del conjunto (delimitadas por el propio elemento central), y la otra mitad pasa a ser el nuevo conjunto. Se repite la operación hasta que se obtiene un conjunto indivisible. */


	/*
	Inserta las palabras reservadas especificadas en el fichero definiciones.h, mediante un array de funciones y otro de constantes
	*/
	void ts_inicializar();


	/*
	Función general que decide si el lexema recibido por argumento se debe insertar en la lista (si no lo encuentra en ella) como identificador. 
	Tanto en el caso de encontrarlo en al lista, o de insertarlo, devuelve un puntero a la estructura lexema del nodo.
	*/
	lexema * ts_encuentra_o_inserta(char s[]);


	/*
	Elimina toda la lista, recorriéndola de forma secuencial y liberando nodo por nodo.
	*/
	void ts_eliminar_lista();


	/*
	Muestra toda la lista, en forma de tabla, de forma secuencial desde el principio, y su longitud total.
	*/
	void ts_mostrar_lista_completa();

	
	void ts_mostrar_lista_variables();
#endif

