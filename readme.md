TODO

use standard wear list to have options for

date in ambient mode
DONE background colour
DONE rotation
DONE tint colour
DONE 24/12 hour mode
reset to defaults

DONE remove old GUI config screen

Next steps:

mobile device config screen



1. DONE remove connascence of algorithm from TDB and persistence tier.
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
 - DONE rotation by config option
 - DONE migrate wear data persistence to use shared prefs
 - possibly introduce value eg rotation degrees or colour value rather than just the name
 - DONE re-write config service to only persist choices, not options. drive from in memory objects only
 - DONE shared prefs persistence
 - (including reset option (presenter responsible for presenting option to use reset method on config service))
 - DONE allow the addition of config options without over writing the current values
    (-allow ability to Add config options not just at the start, ie init defaults should be additive)

 - DONE make config service use the objects rather than persistence
 - DONE remove persistence of other stuff except chosen option
 - DONE get rid of all the string stuff in colour
 - DONE get rid of all the string stuff in rotation

 - DONE When Config Menu Option chosen for colour and rotation, need to announce change

2. Colouring the hours
 - DONE The one where the view is told about the colour
 - DONE The one where you initialise with a default colour
 - DONE The one where you change the colour and its emitted from the system
 - DONE The one where the system remembers the last colour
 - DONE The one where you can configure the colour through the config service
   - tests for each config item via the config service



3. remove duplication in view rendering,
 - collapse interfaces into one
 - make presenters adapt UI interaction into domain events
 - have only one concrete impl (and only one test)

4. introduce a tighter domain model that has objects with:
 - DONE config ID
 - DONE config options list
 - DONE chosen option - this is in each of the submodules
 - DONE can then (de)hydrate this to/from persistence
 - make domain object have bi-directional relationships so can return chosen option with references to rest?
 - possibly change presenters to use this
 - at the very least move from String to ConfigOption type and ConfigId type


5. 12/14 hours
 - DONE toggle
 - DONE Persistence
 - DONE Menu configuring
 - DONE rename all hours mode objects to refer to "mode" rather than "base"
 - DONE config service needs repackaging
 - DONE testworld config option should be public and referenced in tests


 GAP Analysis around ui flow, exiting screen, choosing otions (green tick?)

 -- invalid choice in UI, is it possible?
 -- pre-selecting the current option for item when displayed screen
 -- possibly introduce a none fragment based nav controller and role this into the UI tier tests.
 -- (NO - could the nav controller itself create the presenters and use a view factory.
    How would the fragment generated view get back to the nav controller?
    Consider the case where app is re-hydrating, it feels wrong that the presenter should be long lived
    and infact this would be hard to do, it is tied (and really the same thing) as the fragment,
    therefore the fragment should control the lifecycle of the presenter and the view)

6. Mobile configuration
- DONE UI navigation, one fragment with items, adds alongside another fragment with options
- DONE selecting option should replace options list with confirmation
- DONE options list is removed
- FRAGMENT implementations of the above
 - move TestActivity into android module
  - DI framework needs to go first, although.... maybe not yet.. duplicate then eliminate
- maybe helper panel saying "choose item <--"
(am i missing a test around dep injection in wear, could i move the DI stuff into the view factory?)

--next up, put the right presenter in the Factory
 - consider how we can change the enum factories to compose plain old class factories (maybe just segregate interface)
 - collapse existing View interfaces into one generic ListOfStringsView
 - possible implement re-work of factory enums