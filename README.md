GameShop - Backend Platform
[English Version Below]

GameShop არის Spring Boot-ზე დაფუძნებული ელექტრონული კომერციის პლატფორმის Backend სისტემა, რომელიც შექმნილია თამაშების ციფრული გაყიდვების, მომხმარებლების ბიბლიოთეკისა და ინტერაქტიული რევიუების მართვისთვის.

🚀 ტექნოლოგიური სტეკი
Java 17

Spring Boot 3.x

Spring Data JPA (PostgreSQL / MySQL)

Spring Security

Lombok

Maven

✨ ძირითადი ფუნქციონალი
1. შეკვეთების მართვა & Event-Driven Logic
   Cart & Checkout: სრული ციკლი კალათიდან შეკვეთის გაფორმებამდე.

Spring Events: გამოყენებულია ApplicationEventPublisher სერვისების დეკაპლინგისთვის. შეკვეთის დასრულებისას ავტომატურად იგზავნება იმეილ-შეტყობინება და ახლდება მომხმარებლის ბიბლიოთეკა.

2. რევიუების & რეაქციების სისტემა
   Business Validation: რევიუს დატოვება შეუძლია მხოლოდ იმ მომხმარებელს, ვისაც შეძენილი აქვს კონკრეტული თამაში.

Review Reactions: Like/Dislike სისტემა "Toggle" ლოგიკით (აღრიცხავს დუბლირებულ რეაქციებს).

Rating Aggregation: თამაშის საშუალო რეიტინგის ავტომატური დათვლა ყოველი ახალი რევიუს დროს.

Threaded Comments: მომხმარებლებს შეუძლიათ ერთმანეთის კომენტარებზე პასუხის გაცემა (Reply system).

3. ოპტიმიზაცია & უსაფრთხოება
   Performance: მოგვარებულია N+1 Selection პრობლემა JPA-ში JOIN FETCH-ისა და EntityGraph-ების გამოყენებით.

Data Protection: გამოყენებულია DTO (Data Transfer Object) პატერნი და ვალიდაციები.

🇺🇸 English Version
GameShop is a Spring Boot-based e-commerce backend system designed for digital game sales, user library management, and interactive community features.

🚀 Technology Stack
Java 17 | Spring Boot 3.x | Spring Data JPA

Spring Security | Lombok | Maven

✨ Key Features
Order Management: Full checkout cycle with Spring Events for service decoupling (Automatic email notifications and library updates).

Review System: Complex business validation (purchase-only reviews), Like/Dislike toggle logic, and automatic rating aggregation.

Social Interaction: Support for threaded comments and nested replies.

Performance Optimization: Successfully resolved the N+1 Selection problem using JOIN FETCH and EntityGraphs.

Security: Implementation of the DTO Pattern and strict data validation.

⚙️ Database Configuration / კონფიგურაცია
src/main/resources/application.properties

Properties
spring.datasource.url=jdbc:mysql://localhost:3306/game_shop?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password