TODO

use standard wear list to have options for

background colour
rotation
date in ambient mode
tint colour
24/12 hour mode



Next steps:

1. remove connascence of algorithm from TDB and persistence tier.
 - DONE introduce a store defaults operation (constructor?)
 - DONE write tests to ensure defaults are persisted correctly
 - DONE replace impl in TDB to use this new mechanism
 - DROPPED change persistence interface to talk in terms of a Single String not a list (maybe not a smart move, less adaptive to change?)


todo:
 - DONE allow persisting of choices
 - DONE dont overwrite values if re-initialising
 - DONE ConfigItemsListPresenterTest needs seperating out core service test
 - DONE SelectedConfigItemTest needs seperating out core service test and UI
 - SelectedConfigItemTest needs a choose option test
 - SelectedConfigItemTest needs migrating to fragment



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


 GAP Analysis around ui flow, exiting screen, choosing otions (green tick?)

 -- invalid choice in UI, is it possible?
 -- pre-selecting the current option for item when displayed screen