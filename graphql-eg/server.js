import express from "express";
import { buildSchema } from "graphql";
import { graphqlHTTP } from "express-graphql";

const schema = buildSchema(` 
    type Query {
        hello: String
    }
`)

const rootValue = {
    hello: () => {
        return "Hello World!"
    }
}


const app = express();


app.use('/rehan', graphqlHTTP({
    schema: schema,
    rootValue: rootValue,
    graphiql: true
}))

app.listen(4000);