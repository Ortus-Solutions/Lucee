/**
 * Copyright (c) 2014, the Railo Company Ltd.
 * Copyright (c) 2015, Lucee Assosication Switzerland
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package lucee.runtime.orm;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import lucee.runtime.PageContext;
import lucee.runtime.db.DataSource;
import lucee.runtime.exp.PageException;

public class ORMConnection implements Connection {

	private ORMSession session;
	private boolean autoCommit = false;
	private int isolation;
	private ORMTransaction trans;
	private DataSource ds;
	// private boolean hasBegun;

	/**
	 * Constructor of the class
	 * 
	 * @param session
	 * @param transactionIsolation
	 * @throws PageException
	 */
	public ORMConnection(PageContext pc, ORMSession session, DataSource ds, int transactionIsolation) throws PageException {
		this.session = session;
		this.ds = ds;
		isolation = transactionIsolation;

		trans = session.getTransaction(ds.getName(), session.getEngine().getConfiguration(pc).autoManageSession());
		trans.begin();
	}

	@Override
	public void clearWarnings() throws SQLException {}

	@Override
	public void close() throws SQLException {}

	@Override
	public void commit() {
		trans.commit();
	}

	@Override
	public Statement createStatement() throws SQLException {
		throw notSupported();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		throw notSupported();
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		throw notSupported();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return autoCommit;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return isolation;
	}

	@Override
	public void setTransactionIsolation(int isolation) throws SQLException {
		this.isolation = isolation;
	}

	@Override
	public String getCatalog() throws SQLException {
		throw notSupported();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw notSupported();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		throw notSupported();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		throw notSupported();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw notSupported();
	}

	@Override
	public boolean isClosed() throws SQLException {
		return !session.isValid(ds);
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return sql;
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		throw notSupported();
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw notSupported();
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		throw notSupported();
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		throw notSupported();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		throw notSupported();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		throw notSupported();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		throw notSupported();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw notSupported();
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
		throw notSupported();
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {}

	@Override
	public void rollback() {
		trans.rollback();
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		rollback();
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;

		if (autoCommit) {
			trans.end();
		}
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {}

	@Override
	public void setHoldability(int holdability) throws SQLException {}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		throw notSupported();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		throw notSupported();
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		throw notSupported();
	}

	/*
	 * private SQLException toSQLException(PageException pe) { SQLException e = new
	 * SQLException(pe.getMessage()); e.initCause(pe); return e; }
	 */

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw notSupported();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw notSupported();
	}

	@Override
	public Clob createClob() throws SQLException {
		throw notSupported();
	}

	@Override
	public Blob createBlob() throws SQLException {
		throw notSupported();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		throw notSupported();
	}

	@Override
	public void setClientInfo(String name, String value) {
		throw notSupportedEL();
	}

	@Override
	public void setClientInfo(Properties properties) {
		throw notSupportedEL();
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		throw notSupported();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		throw notSupported();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw notSupported();
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw notSupported();
	}

	private SQLException notSupported() {
		return new SQLFeatureNotSupportedException("this feature is not supported");
	}

	private RuntimeException notSupportedEL() {
		return new RuntimeException(new SQLFeatureNotSupportedException("this feature is not supported"));
	}

	@Override
	public NClob createNClob() throws SQLException {
		throw notSupported();
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		throw notSupported();
	}

	// used only with java 7, do not set @Override
	public void setSchema(String schema) throws SQLException {
		throw notSupported();
	}

	// used only with java 7, do not set @Override
	public String getSchema() throws SQLException {
		throw notSupported();
	}

	// used only with java 7, do not set @Override
	public void abort(Executor executor) throws SQLException {
		throw notSupported();
	}

	// used only with java 7, do not set @Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw notSupported();
	}

	// used only with java 7, do not set @Override
	public int getNetworkTimeout() throws SQLException {
		throw notSupported();
	}
}