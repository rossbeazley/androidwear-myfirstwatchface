TODO

use standard wear list to have options for

background colour
rotation
date in ambient mode
tint colour
24/12 hour mode



Next steps:

1. remove connascence of algorithm from TDB and persistence tier.
 - introduce a store defaults operation (constructor?)
 - write tests to ensure defaults are persisted correctly
 - replace impl in TDB to use this new mechanism
 - change persistence interface to talk in terms of a Single String not a list (maybe not a smart move, less adaptive to change?)


todo:
 - allow persisting of choices
 - dont overwrite values if re-initialising
 - ConfigItemsListPresenterTest needs seperating out core service test


2. remove duplication in view rendering,
 - collapse interfaces into one
 - make presenters adapt UI interaction into domain events
 - have only one concrete impl (and only one test)

3. introduce a tighter domain model that has objects with:
 - config ID
 - config options list
 - chosen option
 - can then (de)hydrate this to/from persistence
 - make domain object have bi-directional relationships so can return chosen option with references to rest?
 - possibly change presenters to use this
 - at the very least move from String to ConfigOption type and ConfigId type