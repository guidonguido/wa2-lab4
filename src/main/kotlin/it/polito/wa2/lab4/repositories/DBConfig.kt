package it.polito.wa2.lab4.repositories


import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

// CONNESSIONE AL DATABASE
@Configuration
@EnableR2dbcRepositories
class DBConfig: AbstractR2dbcConfiguration(){

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        // Posso configurare un ConnectionFactory per il mio DB, specifico
        // In questo modo ad ogni operazione sul DB >> Sarà creata una nuova connessione <<
        /* val conf = MariadbConnectionConfiguration
            .builder()
            .host("localhost")
            .port(3306)
            .username("andrea")
            .password("andrea")
            .database("LAB4")
            .build()
        return MariadbConnectionFactory(conf) */

        // Oppure posso fare una configurazione generica, con >> Pooled driver <<
        // Le connessioni al db saranno chieste al Connection Pool limitando le connessioni istanziabili a quelle
        // fornite dal pool
        return ConnectionFactories.get(
            ConnectionFactoryOptions.builder().apply {
                option(ConnectionFactoryOptions.DRIVER, "pool")
                option(ConnectionFactoryOptions.PROTOCOL, "mariadb")
                option(ConnectionFactoryOptions.HOST, "localhost")
                option(ConnectionFactoryOptions.PORT, 3306)
                option(ConnectionFactoryOptions.USER, "andrea")
                option(ConnectionFactoryOptions.PASSWORD, "andrea")
                option(ConnectionFactoryOptions.DATABASE, "LAB4")
            }.build()
        )
    }

    // Necessario all'inizializzazione dello schema nel DB,
    // non essendo mappata automaticamente del codice come con JPA
    @Bean
    fun initializer(connectionFactory: ConnectionFactory): ConnectionFactoryInitializer {
        val cfi = ConnectionFactoryInitializer()
        cfi.setConnectionFactory(connectionFactory)
        cfi.setDatabasePopulator(
            // Cerca file in resources
            ResourceDatabasePopulator(
                ClassPathResource("schema.sql")
            ))

        return cfi
    }


    /* override fun getCustomConverters(): MutableList<Any> =
        mutableListOf(ProducerReader(), ProducerWriter())

     */
}
