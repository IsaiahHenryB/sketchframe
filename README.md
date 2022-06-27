# Welcome to SketchFrame!

## This is an overview of what went into the development of this application

### Premise

SketchFrame is a simple sandbox style website that allows users to use an abstract art generator to create art and share
it with others. In this site, users can also set whether their artwork is accessible to others or for only them to view.
Each authenticated user has their own page where they can access all of their art and update, download or delete it.

### User Stories

#### ***Complete In This Iteration***

As a Website visitor I want to a have access to the creation page So I can generate art of my own.

As a Website visitor I want to have access to the community creations page So I can view the creations of authenticated
users.

As a Website visitor I want to create an account So I can upload art of my own.

As a Member I want to upload my art So I can access it in the future.

As a Member I want to make my art private So I can be sure that only I can access it.

#### ***To Be Added In A Future Iteration***

As an Admin I want to have access to all artwork So I can appropriately manage each piece.

As a Member I want to update the actual image So I can have more control of what is updated.

As a visitor I want to save my demo canvas So I can upload it when I become authenticated.

### Challenges

Challenge: I wanted users to be able to create "similar to existing" artwork from the art generator.

Solution: I added the parameters used to make the art in the art generator to the artwork entity model. That way, the
parameters could be accessed by the art generator if needed.

Challenge: I wanted to be sure that only the authenticated user can use CRUD operations on their artwork.

Solution: I added @PreAuthorize to the necessary mappings in order tho ensure that CRUD operations could only be done if
the user's credentials matched the data being accessed.