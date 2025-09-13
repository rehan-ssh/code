

/**
 Ways to call graphql client

** in node js env **
graphql({ schema, source: "{hello}", rootValue })
    .then(resp => console.log(resp));

** in cmd **
curl -X POST \
-H "Content-Type: application/json" \
-d '{"query": "{ hello }"}' \
http://localhost:4000/graphql

 */