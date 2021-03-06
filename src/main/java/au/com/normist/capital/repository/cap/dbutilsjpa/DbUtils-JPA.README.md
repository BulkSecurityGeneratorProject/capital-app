DbUtils-JPA brings JPA's easy-to-use mapping annotations to "DbUtils":http://commons.apache.org/dbutils/index.html. It eliminates basic, repetitive SQL, while leaving all the power of DbUtils within easy reach.


h2. Main classes

* "JpaBeanProcessor":https://github.com/mwanji/DbUtils-JPA/blob/master/src/main/java/com/moandjiezana/dbutilsjpa/JpaBeanProcessor.java uses JPA annotations to convert a java.sql.ResultSet to an @Entity.
* "SqlWriter":https://github.com/mwanji/DbUtils-JPA/blob/master/src/main/java/com/moandjiezana/dbutilsjpa/SqlWriter.java uses "sql-writer":https://github.com/mewf/sql-writer to generate SQL.
* "JpaQueryRunner":https://github.com/mwanji/DbUtils-JPA/blob/master/src/main/java/com/moandjiezana/dbutilsjpa/JpaQueryRunner.java provides a friendly, but limited, interface for common operations: get by id, update, insert and delete.

h2. Example

<pre><code>
  JpaQueryRunner queryRunner = new JpaQueryRunner(underlyingQueryRunner); // this instance can be cached and re-used

  MyEntity newEntity = new MyEntity();
  newEntity.setName("Bob");
  newEntity.age = 42;
  queryRunner.save(newEntity); // executes an INSERT
  assert newEntity.getId() == 2L; // the generated primary key has been set
    
  MyEntity savedEntity = queryRunner.query(MyEntity.class, 1L); // executes a SELECT
  savedEntity.setName("Alice");
  savedEntity.age = 39;
  queryRunner.save(savedEntity) // executes an UPDATE
    
  queryRunner.delete(MyEntity.class, savedEntity.getId()); // executes a DELETE
</code></pre>

The JpaQueryRunner always requires that you give it a QueryRunner. It can use a default SqlWriter and RowProcessor, but these can be customised by using the appropriate constructor.

h2. Using elements independently

JpaBeanProcessor can be used apart from JpaQueryRunner, in conjunction with a "RowProcessor":http://commons.apache.org/dbutils/apidocs/org/apache/commons/dbutils/RowProcessor.html and a "ResultSetHandler":http://commons.apache.org/dbutils/apidocs/org/apache/commons/dbutils/ResultSetHandler.html, just like any other "BeanProcessor":http://commons.apache.org/dbutils/apidocs/org/apache/commons/dbutils/BeanProcessor.html, to use JPA mappings in cases not handled by JpaQueryRunner.

<pre><code>ResultSetHandler<List<MyEntity>> handler = new BeanListHandler<MyEntity>(MyEntity.class, new BasicRowProcessor(new JpaBeanProcessor()));
List<MyEntity> myEntities = queryRunner.query("SELECT * FROM MyEntity", handler);</code></pre>

h2. Supported annotations and attributes

* Entity
* Table(name)
* Id
* Column(name, updatable, insertable)
* Transient (and the transient keyword)

h3. Limitations

The semantics of JPA's annotations are followed as much as possible. However, as DbUtils-JPA does not enforce constraints or DDL instructions, certain annotations are ignored, such as @Column#nullable.

Like DbUtils itself, DbUtils-JPA does not map joins. OneToMany, ManyToOne and OneToOne are ignored. This may be added in the future.

To validate your entities, use a Bean Validation API implementation such as "Hibernate Validator":http://www.hibernate.org/subprojects/validator.html or "Apache Bean Validator":http://incubator.apache.org/bval/cwiki/index.html.

h3. Todo

* OrderBy
* Use OneToMany, ManyToOne and OneToOne for joins.
* Basic(fetchType)
* Embeddable, Embedded
* IdClass, EmbeddedId

h2. License

DbUtils-JPA is licensed under the "Apache License, Version 2.0":http://www.apache.org/licenses/LICENSE-2.0.html.
