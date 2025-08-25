erDiagram
    users {
        bigint user_id PK
        varchar user_name
        varchar user_email UK
    }
    
    item_requests {
        bigint request_id PK
        varchar request_description
        bigint user_id FK
        timestamp created
    }
    
    items {
        bigint item_id PK
        varchar item_name
        varchar item_description
        boolean item_available
        bigint user_id FK
        bigint request_id FK
    }
    
    bookings {
        bigint booking_id PK
        timestamp booking_start
        timestamp booking_end
        bigint item_id FK
        bigint user_id FK
        varchar status
    }
    
    comments {
        bigint comment_id PK
        varchar text
        bigint item_id FK
        bigint user_id FK
        timestamp created
    }

    users ||--o{ item_requests : "makes"
    users ||--o{ items : "owns"
    users ||--o{ bookings : "creates"
    users ||--o{ comments : "writes"
    
    item_requests ||--o{ items : "requests"
    
    items ||--o{ bookings : "booked_in"
    items ||--o{ comments : "commented_on"
    
    bookings }|--|| items : "for_item"
    bookings }|--|| users : "by_user"
    
    comments }|--|| items : "about_item"
    comments }|--|| users : "by_user"
    
    item_requests }|--|| users : "requested_by"