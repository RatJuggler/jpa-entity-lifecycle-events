# jpa-entity-lifecycle-events

Experimenting with JPA Entity lifecycle events.

Useful links:

- <https://www.baeldung.com/jpa-entity-lifecycle-events>
- <https://nullbeans.com/how-to-use-prepersist-and-preupdate-in-jpa-hibernate/>
- <https://nullbeans.com/configuring-postload-and-postupdate-in-jpa-hibernate/>
- <https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/package-summary.html>

Pros

- Can be targeted to specific entities.
- Easy to implement and maintain.
- Full access to the entity being loaded / persisted.
- JPA compliant.

Cons

- MAJOR: Leaves the presistence session / entity in a dirty state when you change entity attributes.
