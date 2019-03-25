CS3524_HOME="$(HOME)/3524"

RM_FLAGS="-f"

mud:
	javac cs3524/solutions/mud/MUD.java
	javac cs3524/solutions/mud/Edge.java
	javac cs3524/solutions/mud/Vertex.java
	javac cs3524/solutions/mud/MUD.java
	javac cs3524/solutions/mud/MUDServer.java
	javac cs3524/solutions/mud/MUDServerImpl.java
	javac cs3524/solutions/mud/Client.java

mudclean:
	cd cs3524/solutions/mud; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

rmishout:
	javac cs3524/examples/rmishout/ShoutServerImpl.java; \
	javac cs3524/examples/rmishout/ShoutServerMainline.java; \
	javac cs3524/examples/rmishout/ShoutClient.java

rmishoutclean:
	cd cs3524/examples/rmishout; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

auction:
	javac cs3524/examples/auction/AuctioneerImpl.java; \
	javac cs3524/examples/auction/AuctioneerMainline.java; \
	javac cs3524/examples/auction/BidderImpl.java
	javac cs3524/examples/auction/BidderMainline.java

###########################################################
## The Scissors, Paper, Stone example and solution
##
## Tim Norman
## 2004/09/10
## Revised for java 1.5 2008/01/28
###########################################################

sps:
	javac cs3524/examples/sps/SPSServerImpl.java; \
	javac cs3524/examples/sps/SPSServerMainline.java; \
	javac cs3524/examples/sps/SPSClient.java

spsclean:
	cd cs3524/examples/sps; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

spstar:	spsclean
	rm $(RM_FLAGS) sps.tgz; \
	tar cvf - cs3524/examples/sps sps.policy | gzip > sps.tgz

tpg:
	javac cs3524/solutions/tpg/TPGServerImpl.java; \
	javac cs3524/solutions/tpg/TPGServerMainline.java; \
	javac cs3524/solutions/tpg/TPGClient.java

tpgclean:
	cd cs3524/solutions/tpg; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

tpgtar:	tpgclean
	rm $(RM_FLAGS) tpg.tgz; \
	tar cvf - cs3524/solutions/tpg tpg.policy | gzip > tpg.tgz

###########################################################
## The IRC example and solution
##
## Tim Norman
## 2004/11/24
## Revised for java 1.5 2008/01/28
###########################################################

irc:
	javac cs3524/examples/irc/*.java

ircclean:
	cd cs3524/examples/irc; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

irctar:	ircclean
	rm $(RM_FLAGS) irc.tgz; \
	tar cvf - cs3524/examples/irc | gzip > irc.tgz

ircsoln:
	javac cs3524/solutions/irc/ReqTokenizer.java; \
	javac cs3524/solutions/irc/ChatServer.java; \

ircsolnclean:
	cd cs3524/solutions/irc; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

ircsolntar:	ircsolnclean
	rm $(RM_FLAGS) ircsoln.tgz; \
	tar cvf - cs3524/solutions/irc | gzip > ircsoln.tgz

###########################################################
## The College example and solution
##
## Tim Norman
## 2004/11/24
## Revised for java 1.5 2008/01/28
###########################################################

college:
	javac cs3524/examples/college/CollegeDatabaseInitialiser.java; \
	javac cs3524/examples/college/QueryTool.java

collegeclean:
	cd cs3524/examples/college; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

collegetar:	collegeclean
	rm $(RM_FLAGS) college.tgz; \
	tar cvf - cs3524/examples/college | gzip > college.tgz

collegesoln:
	javac cs3524/solutions/college/CollegeDatabaseInitialiser.java; \
	javac cs3524/solutions/college/QueryTool.java

collegesolnclean:
	cd cs3524/solutions/college; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

collegesolntar:	collegesolnclean
	rm $(RM_FLAGS) collegesoln.tgz; \
	tar cvf - cs3524/solutions/college | gzip > collegesoln.tgz

###########################################################
## The Distributed College example and solution
##
## Tim Norman
## 2004/11/24
## Revised for java 1.5 2008/01/28
## Simplified 2009/02/10
###########################################################

distcollege:
	javac cs3524/examples/distcollege/CollegeDatabaseInitialiser.java; \
	javac cs3524/examples/distcollege/CollegeImpl.java; \
	javac cs3524/examples/distcollege/CollegeMainline.java; \
	javac cs3524/examples/distcollege/QueryTool.java

distcollegeclean:
	cd cs3524/examples/distcollege; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

distcollegetar:	distcollegeclean
	rm $(RM_FLAGS) distcollege.tgz; \
	tar cvf - cs3524/examples/distcollege | gzip > distcollege.tgz

distcollegesoln:
	javac cs3524/solutions/distcollege/CollegeDatabaseInitialiser.java; \
	javac cs3524/solutions/distcollege/CollegeImpl.java; \
	javac cs3524/solutions/distcollege/CollegeMainline.java; \
	javac cs3524/solutions/distcollege/QueryTool.java

distcollegesolnclean:
	cd cs3524/solutions/distcollege; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(CS3524_HOME)

distcollegesolntar:	distcollegesolnclean
	rm $(RM_FLAGS) distcollegesoln.tgz; \
	tar cvf - cs3524/solutions/distcollege | gzip > distcollegesoln.tgz

###########################################################
## The Factory example and solution
##
## Tim Norman
## 2004/11/24
## Revised for java 1.5 2008/01/28
###########################################################

factory:
	javac cs3524/examples/factory/ConnectionImpl.java; \
	javac cs3524/examples/factory/ConnectionFactoryImpl.java; \
	javac cs3524/examples/factory/FactoryMainline.java; \
	javac cs3524/examples/factory/ClientSimulator.java

factoryclean:
	cd cs3524/examples/factory; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(cs3524_HOME)

factorytar:	factoryclean
	rm $(RM_FLAGS) factory.tgz; \
	tar cvf - cs3524/examples/factory factory.policy | gzip > factory.tgz

factorysoln:
	javac cs3524/solutions/factory/IDManager.java; \
	javac cs3524/solutions/factory/ConnectionImpl.java; \
	javac cs3524/solutions/factory/ConnectionFactoryImpl.java; \
	javac cs3524/solutions/factory/FactoryMainline.java; \
	javac cs3524/solutions/factory/ClientSimulator.java

factorysolnclean:
	cd cs3524/solutions/factory; \
	rm $(RM_FLAGS) *.class *~; \
	cd $(cs3524_HOME)

factorysolntar:	factorysolnclean
	rm $(RM_FLAGS) factory.tgz; \
	tar cvf - cs3524/solutions/factory factory.policy | gzip > factorysoln.tgz

###########################################################
## Javadoc
##
## Only for the examples; not the solutions.
###########################################################

javadoc:
	javadoc -d docs cs3524.examples.sps \
			cs3524.examples.irc \
			cs3524.examples.college \
			cs3524.examples.distcollege \
			cs3524.examples.factory

###########################################################
## cleanall
###########################################################

cleanall:	spsclean ircclean collegeclean distcollegeclean factoryclean tpgclean ircsolnclean collegesolnclean distcollegesolnclean factorysolnclean

###########################################################
## tarall
###########################################################

tarall:	rmishouttar spstar collegetar rmishoutsolntar spssolntar collegesolntar
