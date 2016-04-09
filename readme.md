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
 - DONE SelectedConfigItemTest needs a choose option test
 - DONE SelectedConfigItemTest needs migrating to fragment
 - DONE when option selected, announce its success
 - DONE nav controller to display tick on success
 - DONE implement fragment transactions
 - DONE nav controller to somehow go back to start ie implement Option Chosen View
 - DONE ConfigOptionWearViewTest connected test needs to migrate to using the fragment to construct view
 - DONE actually have background colour changed by config option: need to work out who builds who and who owns what options
 - rotation by config option
 - migrate wear data persistence to use shared prefs
 - possibly introduce value eg rotation degrees or colour value rather than just the name
 - re-write config service to only persist choices, not options. drive from in memory objects only
 - shared prefs persistence including reset option (presenter responsible for presenting option to use reset method on config service)
 - allow the addition of config options without over writing the current values

 - make config service use the objects rather than persistence
 - remove persistence of other stuff except chosen option
 - get rid of all the string stuff in colour
 - allow ability to Add config options not just at the start, ie init defaults should be additive

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
 -- possibly introduce a none fragment based nav controller and role this into the UI tier tests.
    could the nav controller itself create the presenters and use a view factory.
    How would the fragment generated view get back to the nav controller?
    Consider the case where app is re-hydrating, it feels wrong that the presenter should be long lived
    and infact this would be hard to do, it is tied (and really the same thing) as the fragment,
    therefore the fragment should control the lifecycle of the presenter and the view