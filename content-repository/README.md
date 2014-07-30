REST API for Repository module
--------------

Provides possibility to save and retrieve Users/Applicants Images and Documents.


##### ImageProcessing:

```sh
resource path: “repo/img/{client}/{ID}”
    method: POST - Adds a new Image in a repository by applicant/user ID
        returns: (json) String with operation status (ResponseEntity<String>)
    method: GET - Provides possibility to get Image with required size
        returns: (base64) Image as Base64 String (ResponseEntity<String>)

    additional path: “/{ID}?height=value1&width=value2”
        method: GET - Provides possibility to get image with required size
            returns: (base64) image as Base64 String (ResponseEntity<String>)
```

##### In case of exception:
```sh
    returns: (json) String with exception info
```

```sh
resource path: “repo/imgfile/{client}/{ID}”
    method: POST - Adds a new Image in a repository by applicant/user ID
        @RequestParam: MultipartFile file
        returns: (json) String with operation status
    method: GET - Provides possibility to get Image with required size
        returns: (image/*) image as byte array (ResponseEntity<byte[]>)
    method: DELETE - Delete Image from repository by applicant/user ID
        returns: (json) String with operation status

    additional path: “/{ID}?height=value1&width=value2”
        method: GET - Provides possibility to get image with required size
            returns: (image/*) Image as byte array (ResponseEntity<byte[]>)
```

##### Document Processing:

```sh
resource path: “repo/doc/{ID}”
    method: POST - Adds a new Document in a repository by applicant ID
        @RequestParam: MultipartFile file
        returns: (json) String with operation status
    method: GET - Provides possibility to get Document
        returns: (optional) Document as byte array (ResponseEntity<byte[]>)
    method: DELETE - Delete Document from repository by applicantID
        returns: (json) String with operation status
```