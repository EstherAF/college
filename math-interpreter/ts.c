#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ts.h"
#include "bison.tab.h"
#include "errores.h"
#include <math.h>

/*Variables globales que conforman la lista*/
ts_nodo *ts_primero;	//puntero al primer elemento de la tabla de simbolos
ts_nodo *ts_ultimo; 	//puntero al �ltimo elemento de la tabla de simbolos
int ts_longitud; 	//longitud de la tabla de s�mbolos. 0=vac�a


/*Arrays de inicializaci�n*/
struct init_funct{
	char * nombre;
	double (*fnct)();
};

struct init_const{
	char * nombre;
	double valor;
};

struct init_funct inicializacion_funciones[]={
	"sin", sin,
	"cos", cos,
	"tan", tan,
	"asin", asin,
	"acos", acos,
	"atan", atan,
	"sqrt", sqrt,
	"sinh", sinh,
	"cosh", cosh,
	"tanh", tanh,
	"log", log,
	"log10", log10,
	"exp", exp,
	0,0
};

struct init_const inicializacion_constantes[]={
	"pi", M_PI,
	"e", M_E,
	0,0
};




/*DECLARACI�N DE LAS FUNCIONES PRIVADAS*/

/*
Desplaza un puntero que apunta a un nodo de la lista, del cual conocemos en qu� posici�n num�rica se encuentra, a otra posici�n num�rica deseada.
Recibe la posici�n num�rica actual del puntero en la lista (posicion_actual), la posici�n num�rica deseada (posicion_nueva), y el propio puntero al nodo (actual)
Devuelve un puntero al nodo en la posici�n num�rica deseada (posicion_nueva).
*/
ts_nodo * ts_desplazar_a(int posicion_actual,int posicion_nueva,ts_nodo *actual);


/*
Reserva memoria para un nuevo nodo y lo inserta en la lista en la posici�n indicada.
Recibe el lexema (s[]), su componente l�xico (comp_lexico), y el nodo anterior a la posici�n de inserci�n (anterior).
Devuelve 0 si hay alg�n fallo, y 1 si todo se realiza correctamente.
*/
ts_nodo * ts_insertar(char s[],int comp_lexico, ts_nodo* anterior);


/*
Busca si un lexema se encuentra en la lista.
Recibe el lexema s.
Devuelve un puntero a su nodo si lo encuentra. 
Si no lo encuentra devuelve un puntero al nodo anterior a la posici�n en la que se deber�a insertar. Devuelve NULL si se debe insertar en primera posici�n.
*/
ts_nodo *ts_buscar(char s[]);

void ts_eliminar_lista();




/*FUNCIONES P�BLICAS*/

/*
Inserta las palabras reservadas especificadas en el fichero definiciones.h
Esta inserci�n siempre se realizar� al principio, por lo que la lista estar� vac�a.
*/
void ts_inicializar(){
	ts_primero=ts_ultimo=NULL; 	//La lista est� vac�a
	int i;
	
	//Inserci�n de las palabras reservadas:
	for (i=0; inicializacion_funciones[i].nombre!=0; i++){ 	
		ts_nodo * anterior=ts_buscar(inicializacion_funciones[i].nombre);		//Buscamos la posici�n en la que insertar la palabra reservada
		ts_nodo * insertado=ts_insertar(inicializacion_funciones[i].nombre,_FNCT,anterior); //Insertamos la palabra reservada, y proporcionamos su n�mero de componente l�xico
		insertado->lexema.contenido.fnct=inicializacion_funciones[i].fnct;
	}

	for (i=0; inicializacion_constantes[i].nombre!=0; i++){ 	
		ts_nodo * anterior=ts_buscar(inicializacion_constantes[i].nombre);		//Buscamos la posici�n en la que insertar la palabra reservada
		ts_nodo * insertado=ts_insertar(inicializacion_constantes[i].nombre,_CONST,anterior); //Insertamos la palabra reservada, y proporcionamos su n�mero de componente l�xico
		insertado->lexema.contenido.valor=inicializacion_constantes[i].valor;

	}

}



/*
Devuelve la estructura lexema correspondiente al nodo del lexema recibido. Si no est� en la lista lo inserta como identificador.
*/
lexema * ts_encuentra_o_inserta(char s[]){
	int comp_lexico,comparacion;
	ts_nodo *nodo;

	ts_nodo *busqueda=ts_buscar(s);  //Buscamos el elemento en la lista, y si no existe, almacenamos la posicion en al que insertar

	if(busqueda==NULL){ 		//Si la b�squeda devuelve NULL, el elemento no existe en la lista, y su posici�n de inserci�n es al principio
		comp_lexico=_ID;			//Al no existir en la lista, tiene que ser un identificador
		nodo=ts_insertar(s,comp_lexico,busqueda); 	//Inserci�n

	}
	else{				//Si devuelve distinto de NULL...
		comparacion=strcmp(s,busqueda->lexema.nombre);		//Comparamos el lexema recibido con el almacenado en el nodo encontrado en al b�squeda

		if(comparacion==0){				//Si son iguales
			comp_lexico=busqueda->lexema.comp_lexico;	//Ha sido encontrado y devolvemos su componente l�xico
			nodo=busqueda;
		}
		else{						//Si son diferentes, el lexema no est� ne la lista, y el nodo obtenido es la posici�n de inserci�n
			comp_lexico=_ID;				
			nodo=ts_insertar(s,comp_lexico,busqueda);		
		}
	}

	return &(nodo->lexema);			//Devolvemos la estructura lexema
}


/*
Elimina toda la lista.
*/
void ts_eliminar_lista(){
	ts_nodo * eliminar, *siguiente;

	eliminar=ts_primero;	//comenzamos a eliminar por el principio de la lista

	while (eliminar!=NULL){	//mientras no se alcance el final de lista

		siguiente=eliminar->siguiente;	//obtenemos el siguiente nodo
		free(eliminar);				//liberamos el actual
		eliminar=siguiente;				

		ts_longitud--;				//actualizamos la longitud
	}
}




/*
Muestra toda la tabla de s�mbolos, incluyendo el valor de las variables y constantes
*/
void ts_mostrar_lista_completa(){
	ts_nodo *nodo=ts_primero;
	int posicion=0;

	printf("\n\nLongitud:%d.\n",ts_longitud); //Longitud
	printf("LEXEMA\tCOMP_LEXICO\tVALOR\tPOSICION\n");	//Columnas

	while(posicion!=ts_longitud){
//		printf("%s\t%d\t%d\n",nodo->lexema.nombre,nodo->lexema.comp_lexico,posicion);
		printf("%s\t\t%d\t",nodo->lexema.nombre,nodo->lexema.comp_lexico);
		if(nodo->lexema.comp_lexico==_ID || nodo->lexema.comp_lexico==_CONST)
			printf("%lf",nodo->lexema.contenido.valor);
		else
			printf("\t");
		printf("\t%d\n",posicion);
		posicion++;
		nodo=nodo->siguiente;
	}
}

/*
Muestra las variables de la tabla de s�mbolos y su valor
*/
void ts_mostrar_lista_variables(){
	ts_nodo *nodo=ts_primero;
	int posicion=0;
	printf("\n\nValor final de las variables utilizadas:\n");	//Columnas

	printf("\nVARIABLE\tVALOR\n");	//Columnas
	printf("-------------------------\n");	//Columnas

	while(posicion!=ts_longitud){
		if(nodo->lexema.comp_lexico==_ID){
			printf("%s\t\t%lf\n",nodo->lexema.nombre,nodo->lexema.contenido.valor);
		}
		posicion++;
		nodo=nodo->siguiente;
	}
}





/*IMPLEMENTACI�N DE LAS FUNCIONES PRIVADAS*/


/*
Inserta el lexema y componente l�xico en un nuevo nodo de la lista, en la posici�n siguiente al nodo recibido.
Devuelve el nodo introducido
*/
ts_nodo * ts_insertar(char s[],int comp_lexico, ts_nodo* anterior){
	ts_nodo *siguiente, *busqueda;

	/*Creaci�n del nuevo nodo*/
	ts_nodo *nuevo_nodo=malloc(sizeof(ts_nodo));	//Reserva de memoria
	nuevo_nodo->lexema.nombre=malloc(sizeof(char)*strlen(s));

	if(nuevo_nodo==NULL)			//Error en la reserva, gestionado por errores.h
		error_identificado(_RESERVA_MEMORIA,0);

	strcpy(nuevo_nodo->lexema.nombre,s);		//Almacenamos los datos
	nuevo_nodo->lexema.comp_lexico=comp_lexico;



	/*Reubicaci�n de los punteros para hacer sitio al nuevo nodo*/
	if(ts_longitud==0){ 				//Inserci�n en lista vac�a
		ts_primero=ts_ultimo=nuevo_nodo;
		siguiente=NULL;

	}else if(anterior==NULL){ 			//Inserci�n como primer elemento de la lista
		siguiente=ts_primero;
		ts_primero=nuevo_nodo;
		siguiente->anterior=nuevo_nodo;

	}else if((siguiente=anterior->siguiente)==NULL){ //Inserci�n como �ltimo elemento de la lista
		ts_ultimo=nuevo_nodo;
		anterior->siguiente=nuevo_nodo;
	}else{						//Inserci�n en el medio de la lista
		anterior->siguiente=nuevo_nodo;
		siguiente->anterior=nuevo_nodo;
	}

	nuevo_nodo->siguiente=siguiente;
	nuevo_nodo->anterior=anterior;

	ts_longitud++; 		//Se actualiza la longitud de la lista
	
	return nuevo_nodo;
}



/*
Desplaza un puntero a un nodo, de una posici�n a otra.
*/
ts_nodo * ts_desplazar_a(int posicion_actual,int posicion_nueva,ts_nodo *actual){
		int i;
		ts_nodo *buscador=actual;

		if (posicion_nueva==0)				//posici�n nueva es el principio de la lista
			buscador=ts_primero;
		
		else if ( posicion_nueva==(ts_longitud-1) )	//posicion nueva es el final de la lista
			buscador=ts_ultimo;

		else if(posicion_actual<posicion_nueva){	//posici�n nueva es mayor que la actual...
			for (i=posicion_actual;i<posicion_nueva;i++)	//recorremos la lista hacia adelante hasta la posici�n nueva
				buscador=buscador->siguiente;
			
		}
		else if (posicion_actual>posicion_nueva){	//posici�n nueva es menor que la actual...
			for (i=posicion_actual;i>posicion_nueva;i-- )	//recorremos la lista hacia atr�s hasta la posici�n nueva
				buscador=buscador->anterior;
			
		}				

		return buscador;	//devolvemos el nodo en la posici�n nueva
}





/*
Encuentra un lexema en la lista o proporciona su posici�n de inserci�n
*/
ts_nodo *ts_buscar(char s[]){
	ts_nodo *buscador;	//puntero a nodo que recorrer� la lista
	
				
	/***Casos concretos***/
	if(ts_longitud==0) 					//Si lista vac�a...
		return NULL;						//se inserta en primera posici�n
	
	else if(strcmp(s,ts_primero->lexema.nombre)<0)
		return NULL;

	else if(strcmp(s,ts_primero->lexema.nombre)==0){	//Si el lexema se encuentra en el primer nodo...
		return ts_primero;					//se devuelve el primer nodo
	}
	else if(strcmp(s,ts_ultimo->lexema.nombre)>=0){		//Si el lexema se encuentra en la �ltima posici�n o se debe insertar despu�s de �sta...
		return ts_ultimo;					//se devuelve el �ltimo nodo
	}
	else{							//En cualquier otro caso...
							
		int inicio,fin; 			//Inicio y fin delimitan el conjunto de b�squeda. 
		int pos_actual_nodo,pos_siguiente_nodo;	//pos_actual_nodo es la posici�n num�rica actual del buscador. 
		int comparacion;			

		inicio=0;	//el bloque de b�squeda inicial es toda la lista
		fin=ts_longitud-1;

		buscador=ts_primero;	//inicializamos buscador al principio de la lista
		pos_actual_nodo=inicio;

		while( (fin-inicio)>1 ){ //Se parar� la b�squeda cuando la diferencia entre inicio y fin sea igual o menor a 1, que es cuando el conjunto de b�squeda es indivisible
			pos_siguiente_nodo=(inicio+fin)/2;		//C�lculo de la posici�n del elemento central del conjunto de b�squeda
			buscador=ts_desplazar_a(pos_actual_nodo,pos_siguiente_nodo,buscador);	//Obtenemos el nodo en la posici�n central...
			pos_actual_nodo=pos_siguiente_nodo;					//y lo almacenamos como nodo actual

			comparacion=strcmp(s,buscador->lexema.nombre); //Examinamos el contenido de esa posici�n compar�ndolo con el lexema a buscar

			if(comparacion==0)	{		//Si el contenido es el mismo: Encontrado.
				return buscador;
		
			}	else if(comparacion>0){			//Si el lexema est� despu�s del nodo que estamos examinando...
				inicio=pos_siguiente_nodo;		//el nuevo bloque de b�squeda es el delimitado entre el medio y el fin
			}
			else if (comparacion<0){		//Si el lexema est� antes del nodo que estamos examinando...
				fin=pos_siguiente_nodo;			//el nuevo bloque de b�squeda es el delimitado entre el inicio y el medio
			}
		}



		/*Ahora mismo, en "comparacion" tenemos la comparaci�n del lexema buscado con el lexema almacenado en el nodo buscador, 
		y el nodo buscador se encuentra en la posicion posicion_nodo_actual*/

		int comparacion_inicio, comparacion_fin;
		ts_nodo *nodo_inicio, *nodo_fin;

		/*Obtenemos los punteros a los nodos en las posiciones inicio y fin, y comparamos el lexema buscado con el almacenado en esos nodos*/

		if (inicio==fin){				//Si inicio y fin son la misma posici�n...
			nodo_inicio=nodo_fin=buscador;			//el nodo inicio y fin son el mismo que buscador
			comparacion_inicio=comparacion_fin=comparacion;	//la comparaci�n del lexema con el del nodo inicio y fin son la misma
		}
		else if (pos_actual_nodo==inicio){		//Si el buscador se qued� en el nodo inicio...
			nodo_inicio=buscador;	
			nodo_fin=nodo_inicio->siguiente;		//el nodo fin es el que est� a continuaci�n del nodo inicio

			comparacion_inicio=comparacion;			//la comparacion obtenida en la b�squeda es la comparaci�n con el lexema del nodo inicio
			comparacion_fin=strcmp(s,nodo_fin->lexema.nombre); 	//comparacion con el nodo fin
		}
		else{						//En otro caso (que el buscador se qued� en el nodo fin)...
			nodo_fin=buscador;
			nodo_inicio=nodo_fin->anterior;			//el nodo inicio es el que est� a antes del nodo fin
			
			comparacion_fin=comparacion;			//la comparacion obtenida en la b�squeda es la comparaci�n con el lexema del nodo fin
			comparacion_inicio=strcmp(s,nodo_inicio->lexema.nombre); 	//comparacion con el nodo inicio

		}

		/*Se devolver� el puntero al nodo anterior a la posicion en la que insertar el nuevo nodo*/
		if(comparacion_inicio>0 && comparacion_fin<0){	//la inserci�n se debe hacer entre inicio y fin..
			return nodo_inicio; 						}	//por lo que se devuelve el nodo inicio
		else if(comparacion_fin>0){ 						//la inserci�n se debe hacer despu�s de la posici�n fin...

			return nodo_fin; }								//por lo que se devuelve el nodo fin
		else if(comparacion_inicio<0){ 					//la inserci�n se debe realizar antes de inicio...
			return nodo_inicio->anterior;} 					//por lo que se devuelve el nodo anterior a inicio
	}
}















