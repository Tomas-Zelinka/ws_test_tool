Spusteni programu

Napoveda:

java -jar FIWS2.0.jar -h




RMI server:

java -Djava.rmi.server.hostname="rozhrani" -jar FIWS2.0.jar -r





Graficke uzivatelske rozhrani:

java -Djava.rmi.server.hostname="rozhrani" FIWS2.0.jar -g




Zakladni test z prikazove radky

java -jar -Djava.rmi.server.hostname="rozhrani" FIWS2.0.jar -testlist "cesta k testovaci sade"




Distribuovany test z prikazove radky 

java -jar -Djava.rmi.server.hostname="rozhrani" FIWS2.0.jar -testlist "cesta k testovaci sade" -config "cesta k souboru s configuraci"




Spusteni proxy monitorovaci jednotky

java -jar -Djava.rmi.server.hostname="rozhrani" FIWS2.0.jar -proxy "cesta k testovacimu pripadu"




Spusteni proxy vice monitorovacich jednotek na ruznych strojich z configurace 

java -jar -Djava.rmi.server.hostname="rozhrani" FIWS2.0.jar -proxy "cesta k testovacimu pripadu" -config "cesta k souboru s configuraci"


Zastaveni vsech proxy monitorovacich jednotek

java -jar -Djava.rmi.server.hostname="rozhrani" FIWS2.0.jar -stop proxy
