# Digital Twin Lab - Alligator Version


## Under Construction

## Under Construction

## Under Construction

# Design

## API

```
TYPE OPERATOR ACTOR LINK
```

The API here is low-level,internal, and hidden from most applications and users behind higher level APIs (graphql) and tooling.

The concepts are: 

  * Types of actors that have surfaces of named doubles.  Types must be created before instantiation of instances via the TYPE API.
  * The named doubles can be observations or derived - derived doubles' processing is defined by the OPERATOR API.
  * DTs are instances of TYPES created with the ACTOR API.
  * Each actor knows its parent and children automatically (via actor runtime / supervision)
  * The LINK API turns the tree of actor instances into a graph.  Once a link is created an aggregate actor can see the changes to state of the linked actor at a specified granularity.  IE: a factory plant actor can see the changes to state of cars it manufactured at a certain level of granularity, ie: daily. (THIS NEEDS SOME THOUGHT but I'm guessing the changes that are visible via subscription include child actor creation - once the aggregate actor is created it will continue to get all the info it needs to watch the evolving system and create new aggregates to watch new sections of the graph.)

### TYPE API

api post schemas (ns, type name, and list of attr names whose values are all doubles)
  
  * name is not a path, nothing says where this hangs in the graph - ns is just to manage collisions, global to system
  * all values are doubles (so far)
  * the actor won't know the difference between derived and observed from its type - its state is just list of named doubles

```
ns is location
type is store
attrs are custs_in_store,open_closed

POST /schema/location/store
["customers_in_store", "open_closed"]

```

### OPERATOR API
  
  * api will define how derived values are created.  Configuring an actor with a DSL/builtin-func and flag indicating eager or lazy (just-in-time vs always correct) execution - open to expressing in Python, R, etc...

  TODO:

### ACTOR API

  * create instances of above types
  * creates are really updates - UPSERTS.
  * creates/updates only init fields if the actor is new - otherwise you must set a field for it to init.
  * creates/updates that have only 1 field will only update that 1 field - supporting telemetry from different sources and late reporters.
  * creates/updates that have only 1 field whose value affects another field via operator triggers recalc of eager operators.

```
ns is location
type is store
instance is store # 4200
2 custs_in_store open store

POST /actor/location.store/4200
{
  "customers_in_store": 24.0,
  "open_closed": 1.0
}

```

### LINK API

this is the least worked out but is needed to support a graph and dynamic aggregates and fusion
