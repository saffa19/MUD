# Multi-User Dungeon/Domain/Dimension

For Dr. Kollingboi

## 19 March 2019
Nothing tested yet.

Getting the structure of the thing sorted out before actually seeing if it works.

Skeleton is completely in place 2am 20th March.

Will try to do the whole thing tomorrow before granite city waterpolo match at 2pm.

## 20 March 2019
Can confirm that this won't be finished by 2pm.
- Error 1: moveThing() defined in MUDServerInterface is missing identifiers -> leads to 'cannot find symbol' error in MUDServerImpl
- Error 2: haven't imported Map to created the hashmap of servers.

#### 20:23 (long day, many beers, waterpolo)
- system can create a MUD instance with a name chosen by the user

#### 23:57 (2 more beers)
- players can move in any direction
- players can ask for help
- players can get info on current location in game
- CGS D complete

## 21 March 2019
####Did CGS B:
- Server list works
- More than one instance of MUD can be generated
- Users can view list and decide if they want to create a new MUD or join an existing one

#### 22:23 still no beer :(
- CGS C done (except for picking things up in the MUD)
- can pick things up now (i.e. delete them from their location)
- state of the MUD is preserved if you keep the server up between reboots
- 