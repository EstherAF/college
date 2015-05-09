Intérprete de cálculos matemáticos
========

Tecnologías: C, con Flex y Bison

Ejecución:

	./a.out <archivo>

Detalles de uso:

	Por defecto, todas las variables tienen valor 0, a menos que se especifique otro valor.
	Al finalizar una línea con ';', el resultado de su ejecución no se muestra en pantalla.
	La herramienta tratará a todos los valores como reales (double).
	Los comentarios se delimitan por '/*' y '*/' (ej /*comentario*/).

Estructura del archivo:

	Las sentencias se pueden separar por salto de línea ('\n') y/o ';'
	Ejemplo de archivo:
		x = 3;
		y = 2 * pi * x
		r = z = x * sin (y + pi/6)

	Salida correspondiente:
		Línea	Resultado	
		2:	18.849556
		3:	1.500000


		Valor final de las variables utilizadas:

		VARIABLE	VALOR
		-------------------------
		r		1.500000
		x		3.000000
		y		18.849556
		z		1.500000





Valores numéricos soportados:

	Enteros
	Reales (ej 3.8)
	Reales en notación científica (ej 3.8E-2)



Nomenclatura de variables soportada:

	Secuencias de números y letras, sin caracteres de puntuación (ej alpha4e)



Funciones soportadas:

	sin(double) - seno de un ángulo en radianes
	cos(double) - coseno de un ángulo en radianes
	tan(double) - tangente de un ángulo en radianes. Su dominio son todos los reales menos (..., -5*pi/2, -3*pi/2, -pi/2, pi/2, 3*pi/2, 5*pi/2, ...)
	asin(double) - arco seno
	acos(double) - arco coseno
	atan(double) - arco tangente
	sqrt(double) - raíz cuadrada
	sinh(double) - seno hiperbólico
	cosh(double) - coseno hiperbólico
	tanh(double) - tangente piperbólica
	log(double) - logaritmo en base e (epsilon)
	log10(double) - logaritmo en base 10
	exp(double) - función exponencial de base e (epsilon)



Constantes declaradas:

	pi - 3.141593
	e - 2.718282, base de los logaritmos naturales

