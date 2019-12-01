#!/bin/bash
curl -v -X POST -H "Content-Type: application/json" --data-binary @body.json localhost:8080/admin/addProduct
