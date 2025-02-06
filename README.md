# Projeto-IV

## Relationship

- User -> Projects -> Texts -> Annotations

## Relationship of SubText with Annotations

- SubTexts
  - Ids Annotations
  - Id Text

## Validations

- User

  - E-mail: Pattern - Not Null
  - Password: 6 characters - Not Null
  - Name: Firstname Lastname - Not Null

- Project

  - Name: Not Null
  - Description: Maybe Null

- Text

  - Value: Not Null

- Annotation

  - Value: Not Null

- Subtext
  - Value: Not Null
  - Annotation: Relationship
