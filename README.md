# Multi-User Dungeon/Domain/Dimension

For Dr. Kollingboi

## 19 March 2019

#### 02:00 (technically the 20th already)
- Skeleton is completely in place 
- Nothing tested yet.
- Will try to do the whole thing tomorrow before granite city waterpolo match at 2pm.

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

#### Did CGS B:
- Server list works
- More than one instance of MUD can be generated
- Users can view list and decide if they want to create a new MUD or join an existing one

#### 22:23 still no beer :(
- CGS C done (except for picking things up in the MUD)
- can pick things up now (i.e. delete them from their location)
- state of the MUD is preserved if you keep the server up between reboots

#### 00:22
- B, C, D completed.
- weird quirk: users can pick each other up
- Onto A5: limit number of users/MUDs and create new MUDs at runtime (in-game)

#### 00:56
- can now create new MUDs at runtime (cleanly)
- quirk: you can pick up yourself (no marks specified for solving this so I'll leave it until last. it doesn't break anything - just messy)
- fix: add a record of users that starts with the server. takeThing() then checks if the thing a user is picking up isn't a user in the userList.
- this list will also come in handy with disconnects/reconnects etc.

## 22 March 2019

#### 15:51
- Maximum allowed number of users/MUDs can be set
- now I add usernames to the takenThings hashmap as they're created (add username with an empty inventory).
- reusing the takenThings hashmap (basically an inventory) to look up things like: total number of users (size of the hashmap's keySet), if a username is taken (hashmap.containsKey())
- ONTO THE REST OF A

#### 16:42
- Login functionality complete.
- Voluntary disconnect is clean.
- now to handle involuntary disconnect (aka unplanned abortion?)

#### 17:50
- Users can't pick up themselves or other users anymore
- issue: because I'm using the takenThings hashmap as a list of "online" users, it isn't updated when users leave a MUD, which is bad..
- fix: when a new user is stored, store their name in a synchronised list (so that their name is removed if their client disconnects)

#### 18:49
- Done
- Synchronised list for record of online users
- disconnect removes user from this list and from the MUD but preserves their inventory
- connect adds user to the list
- REPORT TIME