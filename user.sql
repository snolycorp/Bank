UPDATE users
SET
    username = 'petero',
    password = 'pett',
    email = 'kangogo@gmail.com',
    two_factor_token = '123456',
    two_factor_enabled = TRUE,
    two_factor_verified = TRUE
WHERE
    id = 3; -- Replace with the actual id of johndoe

-- Update vivian's details
UPDATE users
SET
    username = 'vivian',
    password = 'vivian',
    email = 'viviantallam2@gmail.com',
    two_factor_token = '654321',
    two_factor_enabled = FALSE,
    two_factor_verified = FALSE
WHERE
    id = 6; -- Replace with the actual id of vivian
