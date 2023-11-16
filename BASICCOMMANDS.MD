All migration starts with "V{number}__"

To get info:
``` ./gradlew flywayInfo ```

To run the migration:
``` ./gradlew flywayMigrate ```

Whenever you create a migration always create a rollback one, starting with "U{number}__"
``` ./gradlew flywayUndo ``` --> not available on community edition

To repair:
``` ./gradlew flywayRepair ```