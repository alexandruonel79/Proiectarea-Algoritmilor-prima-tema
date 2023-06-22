# Exemplu de Makefile pentru soluții scrise în Java.

.PHONY: build clean

build: Feribot.class Nostory.class Sushi.class Semnale.class

run-p1:
	java Feribot
run-p2:
	java Nostory
run-p3:
	java Sushi
run-p4:
	java Semnale

# Nu uitați să modificați numele surselor.
Feribot.class: 
	javac Feribot.java
	
Nostory.class: 
	javac Nostory.java

Sushi.class: 
	javac Sushi.java

Semnale.class: 
	javac Semnale.java

# Vom șterge fișierele bytecode compilate.
clean:
	rm -f *.class
