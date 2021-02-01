package eu.musicnova.musicnova.web.graphql

import com.expediagroup.graphql.SchemaGeneratorConfig
import com.expediagroup.graphql.TopLevelObject
import com.expediagroup.graphql.toSchema
import eu.musicnova.musicnova.web.graphql.schema.GraphQLDefinition
import eu.musicnova.musicnova.web.graphql.schema.GraphQLMutationDefinition
import eu.musicnova.musicnova.web.graphql.schema.GraphQLQueryDefinition
import eu.musicnova.musicnova.web.graphql.schema.MusicnovaCommonBotQuery
import graphql.GraphQL
import graphql.schema.GraphQLSchema
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component


@Component
class GraphQLProvider {

    @Bean
    fun grapQLSchemaConfig(@Qualifier(GRAPHQL_PACKAGE_LIST_BEAN_NAME) packages: List<String>) = SchemaGeneratorConfig(
        supportedPackages = packages,
    )

    @Bean
    @Qualifier(GRAPHQL_PACKAGE_LIST_BEAN_NAME)
    fun graphQLPackageList(
        definitions: List<GraphQLDefinition>,
    ) = definitions.mapNotNull { definition ->
        definition::class.qualifiedName
            ?.split(".")
            ?.dropLast(1)
            ?.joinToString(".")
    }.distinct()


    @Bean
    fun graphQLSchema(
        config: SchemaGeneratorConfig,
        queryDefinitions: List<GraphQLQueryDefinition>,
        mutatationDefinitions: List<GraphQLMutationDefinition>
    ) = toSchema(
        config = config,
        queries = queryDefinitions.map { def -> TopLevelObject(def) },
        mutations = mutatationDefinitions.map { def -> TopLevelObject(def) }
    )

    @Bean
    fun graphQL(schema: GraphQLSchema): GraphQL = GraphQL.newGraphQL(schema).build()

    companion object {
        const val GRAPHQL_PACKAGE_LIST_BEAN_NAME = "GRAPHQL_PACKAGE_LIST_BEAN"
    }
}