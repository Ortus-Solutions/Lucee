<cfset data = queryNew("ID, DateJoined", "INTEGER, TIMESTAMP", [{ID=1, DateJoined="2017-01-03 10:57:54"}, {ID=2, DateJoined="2017-01-03 10:57:54"}, {ID=3, DateJoined="2017-01-03 10:57:54"}])>
<cfset jsonObject = serializeJSON( Data )>
<cfoutput>#jsonObject#</cfoutput>