todo:
	@bison bison.y --define
	@echo "\nAnalizador sintáctico compilado con bison: bison.tab.c y bison.tab.h"
	@flex flex.l
	@echo "\nAnalizador lexico compilado con flex: lex.yy.c"
	@gcc errores.c ts.c main.c lex.yy.c bison.tab.c -lfl -lm
	@echo "\nGenerado ejecutable a.out. Ejecutar como ./a.out <archivo>"

bison:
	@bison bison.y --define
	@echo "Analizador sintáctico compilado con bison: bison.tab.c"

flex:
	@flex flex.l
	@echo "Analizador lexico compilado con flex: lex.yy.c"

exec:
	gcc errores.c main.c ts.c lex.yy.c bison.tab.c -lfl -lm
	@echo "Generado ejecutable a.out"
