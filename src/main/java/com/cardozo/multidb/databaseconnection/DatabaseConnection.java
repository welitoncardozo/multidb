package com.cardozo.multidb.databaseconnection;

import com.cardozo.multidb.enums.DatabaseId;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;
import java.util.Random;

final class DatabaseConnection {
    @Aspect
    @Configuration
    static class SelectDatabaseAspect {
        @Before("target(com.cardozo.multidb.repositories.anothersdatabase.PeopleRepository)")
        public void before() {
            // Some logic to select the database, this is just an example
            final int randomDatabase = new Random()
                    .ints(1, 3)
                    .findAny()
                    .getAsInt();
            switch (randomDatabase) {
                case 1 -> CurrentDatabase.set(DatabaseId.FIRST);
                case 2 -> CurrentDatabase.set(DatabaseId.SECOND);
                case 3 -> CurrentDatabase.set(DatabaseId.THIRD);
                default -> CurrentDatabase.set(DatabaseId.MAIN);
            }
        }

        @After("target(com.cardozo.multidb.repositories.anothersdatabase.PeopleRepository)")
        public void after() {
            CurrentDatabase.set(DatabaseId.MAIN);
        }
    }

    public static class MultiRoutingDataSource extends AbstractRoutingDataSource {
        private MultiRoutingDataSource(final Map<Object, Object> dataSources) {
            setTargetDataSources(dataSources);
            setDefaultTargetDataSource(dataSources.values()
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Need to define at least one data source"))
            );
        }

        public static MultiRoutingDataSource getNewInstance(final Map<Object, Object> dataSources) {
            return new MultiRoutingDataSource(dataSources);
        }

        @Override
        protected Object determineCurrentLookupKey() {
            return CurrentDatabase.get();
        }
    }

    private static class CurrentDatabase {
        private static final ThreadLocal<DatabaseId> context = ThreadLocal.withInitial(() -> DatabaseId.MAIN);

        public static void set(final DatabaseId databaseId) {
            context.set(databaseId);
        }

        public static DatabaseId get() {
            return context.get();
        }
    }
}
