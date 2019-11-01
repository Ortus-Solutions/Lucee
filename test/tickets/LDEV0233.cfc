component extends="org.lucee.cfml.test.LuceeTestCase"{
	function run(){
		describe( title="Test suite for LDEV-233", skip=checkMySqlEnvVarsAvailable(), body=function(){
			it(title="Checking ORM with cftransaction", body=function(){
				var uri = createURI("LDEV0233/withTrans.cfm");
				var result = _InternalRequest(
					template:uri
				);
				expect(result.fileContent.trim()).toBeTrue();
			});

			it(title="Checking ORM without cftransaction", body=function(){
				var uri = createURI("LDEV0233/withoutTrans.cfm");
				var result = _InternalRequest(
					template:uri
				);
				expect(result.fileContent.trim()).toBeTrue();
			});
		});
	}

	// Private functions
	private string function createURI(string calledName){
		var baseURI = "/test/#listLast(getDirectoryFromPath(getCurrenttemplatepath()),"\/")#/";
		return baseURI & "" & calledName;
	}

	private boolean function checkMySqlEnvVarsAvailable() {
		// getting the credentials from the environment variables
		var mySQL={};
		if(isNull(server.system)){
			server.system = structNew();
			currSystem = createObject("java", "java.lang.System");
			server.system.environment = currSystem.getenv();
			server.system.properties = currSystem.getproperties();
		}

		if(
			!isNull(server.system.environment.MYSQL_SERVER) &&
			!isNull(server.system.environment.MYSQL_USERNAME) &&
			!isNull(server.system.environment.MYSQL_PASSWORD) &&
			!isNull(server.system.environment.MYSQL_PORT) &&
			!isNull(server.system.environment.MYSQL_DATABASE)) {
			mySQL.server=server.system.environment.MYSQL_SERVER;
			mySQL.username=server.system.environment.MYSQL_USERNAME;
			mySQL.password=server.system.environment.MYSQL_PASSWORD;
			mySQL.port=server.system.environment.MYSQL_PORT;
			mySQL.database=server.system.environment.MYSQL_DATABASE;
		}
		// getting the credentials from the system variables
		else if(
			!isNull(server.system.properties.MYSQL_SERVER) &&
			!isNull(server.system.properties.MYSQL_USERNAME) &&
			!isNull(server.system.properties.MYSQL_PASSWORD) &&
			!isNull(server.system.properties.MYSQL_PORT) &&
			!isNull(server.system.properties.MYSQL_DATABASE)) {
			mySQL.server=server.system.properties.MYSQL_SERVER;
			mySQL.username=server.system.properties.MYSQL_USERNAME;
			mySQL.password=server.system.properties.MYSQL_PASSWORD;
			mySQL.port=server.system.properties.MYSQL_PORT;
			mySQL.database=server.system.properties.MYSQL_DATABASE;
		}
		return structIsEmpty(mySQL);
	}
}