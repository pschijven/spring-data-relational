/*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.jdbc.core.dialect;

import java.sql.JDBCType;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcValue;
import org.springframework.data.relational.core.dialect.Db2Dialect;
import org.springframework.data.relational.core.dialect.MySqlDialect;
import org.springframework.data.relational.core.sql.IdentifierProcessing;

/**
 * {@link Db2Dialect} that registers JDBC specific converters.
 *
 * @author Jens Schauder
 * @since 2.3
 */
public class JdbcMySqlDialect extends MySqlDialect {

	public JdbcMySqlDialect(IdentifierProcessing identifierProcessing) {
		super(identifierProcessing);
	}

	@Override
	public Collection<Object> getConverters() {

		ArrayList<Object> converters = new ArrayList<>(super.getConverters());
		converters.add(OffsetDateTime2TimestampJdbcValueConverter.INSTANCE);

		return converters;
	}

	@WritingConverter
	enum OffsetDateTime2TimestampJdbcValueConverter implements Converter<OffsetDateTime, JdbcValue> {
		INSTANCE;

		@Override
		public JdbcValue convert(OffsetDateTime source) {
			return JdbcValue.of(source, JDBCType.TIMESTAMP);
		}
	}
}