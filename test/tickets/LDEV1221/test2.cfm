<cfscript>
	dynamiVar = 'static';

	try {

		data = "#evaluate( dynamiVar )#"::getData();
		writeOutput(data.bar);
	} catch ( any e ) {
		
		writeOutput( e.message );
	}
	
</cfscript>
