Requisitos:
- Java 17+
- Driver PostgreSQL (postgresql-42.x.x.jar) en classpath
- Base de datos conceptos_m creada y script SQL aplicado

Compilación (ejemplo):
javac -cp .;postgresql-42.7.2.jar src\db\DBConnection.java src\model\*.java src\dao\ConsultasDAO.java src\Main.java

Ejecución:
java -cp .;postgresql-42.7.2.jar Main

Notas:
- Ajustar host/puerto si difiere.
- El enum employee_role_status debe existir si se usa en Empleado.
