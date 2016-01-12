# EclipsePluginMetamodelProposals
Eclipse plugin to provide autocomplete proposals for Metamodel entities and attributes

The Idea is to create an Eclipse plugin that provides completion proposals in the java editor for Metamodel Entries instead of using generated object provided by the MetamodelGenerator.
I want to decouple our code from the metamodel so that metamodel changes can be depoyed separated from the code.

The current version is a POC to see how this can work.

---Working Progress---
Features
* java editor metamodel proposals
* Markers:
** when the Metamodel updates, we want and marker on our usages
** Marker with quick fix to convert the usage of metamodel generated object to the "new" way
* some basic testcase support to create testcases that checks if the "live" metamodel still contains the entries used in code so the build could fail if there's a mismatch.
