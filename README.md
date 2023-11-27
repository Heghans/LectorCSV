# LectorCSV
Este es un lector de archivos CSV, dirigido a lo que es la lectura de datos de una cafeteria, con la intencion de realizar un desglose total de las 3 tipos de areas de ventas que posee.
Locales, Entrega y Retiro, este codigo fue hecho por Melvin Josué Alfaro y Sebastian Alberto Loria
Se utilizo para el lector de CSV las bibliotecas externas de OpenCSV y sus dependencias

ATENCION
Estos comandos son con los cuales ejecutamos el codigo, tenemos que tener en cuenta varios factores al ejecutarlo en terminal.
el principal es que en el comando, agregamos la direccion de los CSV a leer, a la vez que a la hora de compilar y ejecutar, tenemos que agregar la direccion de jar_files, ya que esta carpeta es la que contiene todas las dependencias y la propia biblioteca de OpenCsv que se utilizo para este proyecto.


EJEMPLO DE COMO SE UTILIZA EL COMANDO DE EJECUCION
java -cp "C:\ejemplo\de\direccion\jar_files\*;." Objetos.AnalisisVentas -l C:\ejemplo\de\direccion\ventas_en_local.csv -r C:\ejemplo\de\direccion\ventas_para_retirar.csv -e C:\ejemplo\de\direccion\ventas_con_entrega.csv -m 6


EJEMPLO DE COMO SE UTILIZA EL COMANDO DE COMPILACION
javac -cp "C:\ejemplo\de\direccion\jar_files\*" *.java


COMANDOS DE COMPILACION

Directorio a estar para compilar: cd C:\Users\josue\IdeaProjects\LectorCvs\src\Objetos

Compilar:  javac -cp "C:\Users\josue\Desktop\jar_files\*" *.java

Volver a directorio de salida: cd C:\Users\josue\IdeaProjects\LectorCvs\out\production\LectorCvs

Ejecucion: java -cp "C:\Users\josue\Desktop\jar_files\*;." Objetos.AnalisisVentas -l C:\Users\josue\Downloads\ventas_en_local.csv -r C:\Users\josue\Downloads\ventas_para_retirar.csv -e C:\Users\josue\Downloads\ventas_con_entrega.csv -m 6
