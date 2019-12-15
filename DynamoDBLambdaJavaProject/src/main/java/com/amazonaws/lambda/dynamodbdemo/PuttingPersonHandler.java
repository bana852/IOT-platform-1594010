package com.amazonaws.lambda.dynamodbdemo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class PuttingPersonHandler implements RequestHandler<Person, String> {
    private DynamoDB dynamoDb;
    private String TABLE_NAME = "People";
    private String REGION = "us-east-1";

    @Override
    public String handleRequest(Person input, Context context) {
        this.initDynamoDbClient();

        putData(input);
        return "Saved Successfully!!";
    }

    private void initDynamoDbClient() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(REGION).build();
         this.dynamoDb = new DynamoDB(client);
    }

    private PutItemOutcome putData(Person person) 
              throws ConditionalCheckFailedException {
                return this.dynamoDb.getTable(TABLE_NAME)
                  .putItem(
                    new PutItemSpec().withItem(new Item()
                            .withPrimaryKey("id",person.id)
                            .withString("firstName", person.firstName)
                            .withString("lastName", person.lastName)));
            }
}

class Person {
    public String firstName;
    public String lastName;
    public int id;
}