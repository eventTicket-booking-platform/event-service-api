
-- Seed data for local/demo event-service-api runs.
-- This script is idempotent: existing categories, venues, events, and ticket types are not duplicated.

INSERT INTO categories (name, description, is_active)
SELECT 'Music', 'Live concerts, acoustic nights, DJ sessions, and music festivals across Sri Lanka.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Music');

INSERT INTO categories (name, description, is_active)
SELECT 'Technology', 'Tech conferences, startup meetups, coding workshops, and innovation showcases.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Technology');

INSERT INTO categories (name, description, is_active)
SELECT 'Sports', 'Live sports events, matches, tournaments, and athletic competitions.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Sports');

INSERT INTO categories (name, description, is_active)
SELECT 'Art', 'Art exhibitions, galleries, creative showcases, and cultural experiences.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Art');

INSERT INTO categories (name, description, is_active)
SELECT 'Food', 'Food festivals, culinary events, tastings, and dining experiences.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Food');

INSERT INTO categories (name, description, is_active)
SELECT 'Comedy', 'Stand-up shows, comedy nights, improv events, and live entertainment.', TRUE
WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Comedy');

-- Venues
INSERT INTO venues (name, city, address)
SELECT 'Nelum Pokuna Open Grounds', 'Colombo', 'Nelum Pokuna Mawatha, Colombo 07'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Nelum Pokuna Open Grounds' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Galle Face Green Arena', 'Colombo', 'Galle Face Green, Colombo 03'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Galle Face Green Arena' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Kandy Lake Club Stage', 'Kandy', 'Sangaraja Mawatha, Kandy'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Kandy Lake Club Stage' AND city = 'Kandy');

INSERT INTO venues (name, city, address)
SELECT 'Jaffna Cultural Hall', 'Jaffna', 'Hospital Road, Jaffna'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Jaffna Cultural Hall' AND city = 'Jaffna');

INSERT INTO venues (name, city, address)
SELECT 'Galle Fort Courtyard', 'Galle', 'Church Street, Galle Fort'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Galle Fort Courtyard' AND city = 'Galle');

INSERT INTO venues (name, city, address)
SELECT 'BMICH Innovation Hall', 'Colombo', 'Bauddhaloka Mawatha, Colombo 07'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'BMICH Innovation Hall' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Trace Expert City', 'Colombo', 'Maradana, Colombo 10'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Trace Expert City' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'SLIIT Tech Auditorium', 'Malabe', 'New Kandy Road, Malabe'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'SLIIT Tech Auditorium' AND city = 'Malabe');

INSERT INTO venues (name, city, address)
SELECT 'Peradeniya Innovation Centre', 'Kandy', 'University of Peradeniya, Peradeniya'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Peradeniya Innovation Centre' AND city = 'Kandy');

INSERT INTO venues (name, city, address)
SELECT 'Galle Tech Hub', 'Galle', 'Wakwella Road, Galle'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Galle Tech Hub' AND city = 'Galle');

INSERT INTO venues (name, city, address)
SELECT 'Sugathadasa Indoor Stadium', 'Colombo', 'Sugathadasa Mawatha, Colombo 13'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Sugathadasa Indoor Stadium' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Racecourse Sports Complex', 'Colombo', 'Reid Avenue, Colombo 07'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Racecourse Sports Complex' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Pallekele Community Grounds', 'Kandy', 'Pallekele, Kandy'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Pallekele Community Grounds' AND city = 'Kandy');

INSERT INTO venues (name, city, address)
SELECT 'Dambulla Sports Park', 'Dambulla', 'Kandalama Road, Dambulla'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Dambulla Sports Park' AND city = 'Dambulla');

INSERT INTO venues (name, city, address)
SELECT 'Matara Beach Sports Arena', 'Matara', 'Beach Road, Matara'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Matara Beach Sports Arena' AND city = 'Matara');

INSERT INTO venues (name, city, address)
SELECT 'Colombo Art Gallery', 'Colombo', 'Green Path, Colombo 07'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Colombo Art Gallery' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Barefoot Gallery Garden', 'Colombo', 'Galle Road, Colombo 03'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Barefoot Gallery Garden' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Kandy Heritage Arts Centre', 'Kandy', 'Dalada Veediya, Kandy'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Kandy Heritage Arts Centre' AND city = 'Kandy');

INSERT INTO venues (name, city, address)
SELECT 'Jaffna Art House', 'Jaffna', 'Kankesanthurai Road, Jaffna'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Jaffna Art House' AND city = 'Jaffna');

INSERT INTO venues (name, city, address)
SELECT 'Galle Fort Art Walk', 'Galle', 'Pedlar Street, Galle Fort'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Galle Fort Art Walk' AND city = 'Galle');

INSERT INTO venues (name, city, address)
SELECT 'Colombo Street Food Park', 'Colombo', 'Marine Drive, Colombo 04'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Colombo Street Food Park' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Kandy Spice Garden', 'Kandy', 'Katugastota Road, Kandy'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Kandy Spice Garden' AND city = 'Kandy');

INSERT INTO venues (name, city, address)
SELECT 'Galle Seafood Court', 'Galle', 'Galle Fort Rampart, Galle'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Galle Seafood Court' AND city = 'Galle');

INSERT INTO venues (name, city, address)
SELECT 'Jaffna Taste Market', 'Jaffna', 'Main Street, Jaffna'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Jaffna Taste Market' AND city = 'Jaffna');

INSERT INTO venues (name, city, address)
SELECT 'Negombo Lagoon Food Yard', 'Negombo', 'Lewis Place, Negombo'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Negombo Lagoon Food Yard' AND city = 'Negombo');

INSERT INTO venues (name, city, address)
SELECT 'Colombo Laugh Lounge', 'Colombo', 'Park Street, Colombo 02'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Colombo Laugh Lounge' AND city = 'Colombo');

INSERT INTO venues (name, city, address)
SELECT 'Kandy Comedy Cellar', 'Kandy', 'William Gopallawa Mawatha, Kandy'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Kandy Comedy Cellar' AND city = 'Kandy');

INSERT INTO venues (name, city, address)
SELECT 'Galle Fort Comedy Room', 'Galle', 'Lighthouse Street, Galle Fort'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Galle Fort Comedy Room' AND city = 'Galle');

INSERT INTO venues (name, city, address)
SELECT 'Jaffna Open Mic Hall', 'Jaffna', 'Stanley Road, Jaffna'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Jaffna Open Mic Hall' AND city = 'Jaffna');

INSERT INTO venues (name, city, address)
SELECT 'Negombo Weekend Theatre', 'Negombo', 'Beach Road, Negombo'
WHERE NOT EXISTS (SELECT 1 FROM venues WHERE name = 'Negombo Weekend Theatre' AND city = 'Negombo');

-- Events: Music
INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Colombo Sunset Beats Festival', 'A waterfront evening of Sri Lankan pop, baila, and DJ sets with food stalls and family seating.', c.id, v.id, '2026-07-18 18:00:00', '2026-07-18 23:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Galle Face Green Arena' AND v.city = 'Colombo'
WHERE c.name = 'Music' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Colombo Sunset Beats Festival');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Kandy Acoustic Mountain Night', 'An intimate acoustic show featuring folk artists, soft rock bands, and scenic hill country vibes.', c.id, v.id, '2026-08-08 17:30:00', '2026-08-08 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Kandy Lake Club Stage' AND v.city = 'Kandy'
WHERE c.name = 'Music' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Kandy Acoustic Mountain Night');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Jaffna Fusion Music Evening', 'A northern fusion concert blending Tamil folk, percussion, classical vocals, and modern instrumentals.', c.id, v.id, '2026-08-22 18:30:00', '2026-08-22 22:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Jaffna Cultural Hall' AND v.city = 'Jaffna'
WHERE c.name = 'Music' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Jaffna Fusion Music Evening');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Galle Fort Jazz Weekend', 'A stylish jazz and blues showcase inside the historic fort with sunset sessions and lounge seating.', c.id, v.id, '2026-09-05 16:00:00', '2026-09-05 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Galle Fort Courtyard' AND v.city = 'Galle'
WHERE c.name = 'Music' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Galle Fort Jazz Weekend');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Nelum Pokuna Indie Live', 'A live showcase of emerging Sri Lankan indie bands, singer-songwriters, and experimental performers.', c.id, v.id, '2026-09-19 18:00:00', '2026-09-19 23:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Nelum Pokuna Open Grounds' AND v.city = 'Colombo'
WHERE c.name = 'Music' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Nelum Pokuna Indie Live');

-- Events: Technology
INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Colombo Cloud Native Summit', 'A practical summit on cloud deployments, microservices, observability, and platform engineering.', c.id, v.id, '2026-07-25 09:00:00', '2026-07-25 17:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'BMICH Innovation Hall' AND v.city = 'Colombo'
WHERE c.name = 'Technology' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Colombo Cloud Native Summit');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Trace Startup Demo Day', 'Local startups pitch new products in fintech, travel, agriculture, and event technology.', c.id, v.id, '2026-08-01 10:00:00', '2026-08-01 16:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Trace Expert City' AND v.city = 'Colombo'
WHERE c.name = 'Technology' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Trace Startup Demo Day');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Malabe AI Builders Workshop', 'A hands-on AI workshop covering prompts, APIs, model evaluation, and prototype demos.', c.id, v.id, '2026-08-15 09:30:00', '2026-08-15 15:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'SLIIT Tech Auditorium' AND v.city = 'Malabe'
WHERE c.name = 'Technology' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Malabe AI Builders Workshop');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Peradeniya Robotics Expo', 'Student teams and robotics clubs demonstrate autonomous bots, drones, and embedded systems.', c.id, v.id, '2026-09-12 09:00:00', '2026-09-12 18:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Peradeniya Innovation Centre' AND v.city = 'Kandy'
WHERE c.name = 'Technology' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Peradeniya Robotics Expo');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Galle Digital Creators Lab', 'A creator-tech event for web makers, video editors, designers, and online entrepreneurs.', c.id, v.id, '2026-09-26 10:00:00', '2026-09-26 17:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Galle Tech Hub' AND v.city = 'Galle'
WHERE c.name = 'Technology' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Galle Digital Creators Lab');

-- Events: Sports
INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Colombo Futsal Champions Cup', 'An energetic indoor futsal tournament featuring community clubs and school alumni teams.', c.id, v.id, '2026-07-26 14:00:00', '2026-07-26 20:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Sugathadasa Indoor Stadium' AND v.city = 'Colombo'
WHERE c.name = 'Sports' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Colombo Futsal Champions Cup');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Racecourse Rugby Sevens', 'A fast-paced rugby sevens showcase with club teams, food vendors, and evening entertainment.', c.id, v.id, '2026-08-09 13:00:00', '2026-08-09 19:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Racecourse Sports Complex' AND v.city = 'Colombo'
WHERE c.name = 'Sports' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Racecourse Rugby Sevens');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Kandy Hill Country Cricket Bash', 'A friendly cricket bash with local teams, music breaks, and family picnic zones.', c.id, v.id, '2026-08-29 10:00:00', '2026-08-29 18:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Pallekele Community Grounds' AND v.city = 'Kandy'
WHERE c.name = 'Sports' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Kandy Hill Country Cricket Bash');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Dambulla Cycling Challenge', 'A scenic cycling event through central Sri Lanka with amateur, junior, and open categories.', c.id, v.id, '2026-09-13 06:30:00', '2026-09-13 12:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Dambulla Sports Park' AND v.city = 'Dambulla'
WHERE c.name = 'Sports' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Dambulla Cycling Challenge');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Matara Beach Volleyball Open', 'A coastal volleyball open with mixed teams, beach games, and sunset award ceremony.', c.id, v.id, '2026-09-27 08:00:00', '2026-09-27 17:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Matara Beach Sports Arena' AND v.city = 'Matara'
WHERE c.name = 'Sports' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Matara Beach Volleyball Open');

-- Events: Art
INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Colombo Contemporary Canvas', 'A curated contemporary art exhibition featuring young painters, sculptors, and mixed-media artists.', c.id, v.id, '2026-07-19 10:00:00', '2026-07-19 19:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Colombo Art Gallery' AND v.city = 'Colombo'
WHERE c.name = 'Art' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Colombo Contemporary Canvas');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Barefoot Handmade Weekend', 'A weekend market and workshop series for textiles, ceramics, illustration, and handmade products.', c.id, v.id, '2026-08-02 11:00:00', '2026-08-02 18:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Barefoot Gallery Garden' AND v.city = 'Colombo'
WHERE c.name = 'Art' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Barefoot Handmade Weekend');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Kandy Heritage Craft Fair', 'Traditional craft demonstrations, mask painting, brassware, batik, and cultural performances.', c.id, v.id, '2026-08-23 09:30:00', '2026-08-23 17:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Kandy Heritage Arts Centre' AND v.city = 'Kandy'
WHERE c.name = 'Art' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Kandy Heritage Craft Fair');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Jaffna Mural Stories', 'A visual storytelling exhibition inspired by northern landscapes, street art, and community history.', c.id, v.id, '2026-09-06 10:00:00', '2026-09-06 18:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Jaffna Art House' AND v.city = 'Jaffna'
WHERE c.name = 'Art' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Jaffna Mural Stories');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Galle Fort Art Walk', 'An evening art walk through fort galleries with live sketching, pop-up studios, and guided tours.', c.id, v.id, '2026-09-20 16:00:00', '2026-09-20 21:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Galle Fort Art Walk' AND v.city = 'Galle'
WHERE c.name = 'Art' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Galle Fort Art Walk');

-- Events: Food
INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Colombo Street Food Fiesta', 'A lively night market of kottu, hoppers, isso wade, desserts, and live kitchen battles.', c.id, v.id, '2026-07-20 17:00:00', '2026-07-20 23:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Colombo Street Food Park' AND v.city = 'Colombo'
WHERE c.name = 'Food' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Colombo Street Food Fiesta');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Kandy Spice Trail Experience', 'A tasting experience featuring hill country spices, cooking demos, and tea pairing sessions.', c.id, v.id, '2026-08-16 11:00:00', '2026-08-16 17:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Kandy Spice Garden' AND v.city = 'Kandy'
WHERE c.name = 'Food' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Kandy Spice Trail Experience');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Galle Seafood Sundown', 'Fresh seafood tastings, chef counters, mocktail bars, and sunset dining by the fort ramparts.', c.id, v.id, '2026-08-30 16:30:00', '2026-08-30 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Galle Seafood Court' AND v.city = 'Galle'
WHERE c.name = 'Food' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Galle Seafood Sundown');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Jaffna Taste Market', 'A northern food showcase with crab curry, dosai, kool, sweets, and family-style dining.', c.id, v.id, '2026-09-14 12:00:00', '2026-09-14 20:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Jaffna Taste Market' AND v.city = 'Jaffna'
WHERE c.name = 'Food' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Jaffna Taste Market');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Negombo Lagoon Food Fest', 'A relaxed lagoon-side food festival with seafood grills, local desserts, and live acoustic music.', c.id, v.id, '2026-09-28 15:00:00', '2026-09-28 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Negombo Lagoon Food Yard' AND v.city = 'Negombo'
WHERE c.name = 'Food' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Negombo Lagoon Food Fest');

-- Events: Comedy
INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Colombo Friday Laughs', 'A Friday night lineup of Sri Lankan stand-up comics, crowd games, and late-night laughs.', c.id, v.id, '2026-07-24 19:00:00', '2026-07-24 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Colombo Laugh Lounge' AND v.city = 'Colombo'
WHERE c.name = 'Comedy' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Colombo Friday Laughs');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Kandy Punchline Night', 'A hill country comedy evening with openers, headline acts, and audience improv games.', c.id, v.id, '2026-08-07 18:30:00', '2026-08-07 21:30:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Kandy Comedy Cellar' AND v.city = 'Kandy'
WHERE c.name = 'Comedy' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Kandy Punchline Night');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Galle Fort Comedy Roast', 'A playful roast-style comedy night with local performers and clean crowd interaction.', c.id, v.id, '2026-08-21 19:00:00', '2026-08-21 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Galle Fort Comedy Room' AND v.city = 'Galle'
WHERE c.name = 'Comedy' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Galle Fort Comedy Roast');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Jaffna Open Mic Comedy', 'A community open mic comedy night with short sets, first-timers, and guest performers.', c.id, v.id, '2026-09-04 18:00:00', '2026-09-04 21:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Jaffna Open Mic Hall' AND v.city = 'Jaffna'
WHERE c.name = 'Comedy' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Jaffna Open Mic Comedy');

INSERT INTO events (title, description, category_id, venue_id, start_datetime, end_datetime, status, created_by, created_at, updated_at)
SELECT 'Negombo Weekend Comedy Club', 'A weekend comedy club show with observational humor, improv bits, and beach-town stories.', c.id, v.id, '2026-09-18 19:00:00', '2026-09-18 22:00:00', 'PUBLISHED', 'seed-admin', NOW(), NOW()
FROM categories c JOIN venues v ON v.name = 'Negombo Weekend Theatre' AND v.city = 'Negombo'
WHERE c.name = 'Comedy' AND NOT EXISTS (SELECT 1 FROM events WHERE title = 'Negombo Weekend Comedy Club');

-- Ticket types: two ticket tiers for every seeded event.
INSERT INTO ticket_types (name, price, total_quantity, available_quantity, event_id)
SELECT 'Standard', 2500.00, 120, 120, e.id FROM events e
WHERE e.created_by = 'seed-admin'
  AND NOT EXISTS (SELECT 1 FROM ticket_types tt WHERE tt.event_id = e.id AND tt.name = 'Standard');

INSERT INTO ticket_types (name, price, total_quantity, available_quantity, event_id)
SELECT 'VIP', 5000.00, 40, 40, e.id FROM events e
WHERE e.created_by = 'seed-admin'
  AND NOT EXISTS (SELECT 1 FROM ticket_types tt WHERE tt.event_id = e.id AND tt.name = 'VIP');

-- BEGIN GENERATED EVENT BANNER SEED
-- Event banner metadata for event-service-api.
-- Upload images first, then run this SQL if you do not use APPLY_DB=true.
-- directory intentionally ends with '/' because Java deletes with directory + fileName.

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('01-colombo-sunset-beats-festival.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/01-colombo-sunset-beats-festival.png' AS BINARY),
    b.hash = CAST('bd96d5b71e76c930ef3a12b87ec7be2dcc3b377da162463fc2067fb012f0b653' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Colombo Sunset Beats Festival';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('01-colombo-sunset-beats-festival.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/01-colombo-sunset-beats-festival.png' AS BINARY), CAST('bd96d5b71e76c930ef3a12b87ec7be2dcc3b377da162463fc2067fb012f0b653' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Colombo Sunset Beats Festival'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('02-kandy-acoustic-mountain-night.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/02-kandy-acoustic-mountain-night.png' AS BINARY),
    b.hash = CAST('d9daf794b615e3c925b83fa1c32efcbb736fac2c9b0948be60a33e9fa66870b2' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Kandy Acoustic Mountain Night';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('02-kandy-acoustic-mountain-night.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/02-kandy-acoustic-mountain-night.png' AS BINARY), CAST('d9daf794b615e3c925b83fa1c32efcbb736fac2c9b0948be60a33e9fa66870b2' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Kandy Acoustic Mountain Night'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('03-jaffna-fusion-music-evening.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/03-jaffna-fusion-music-evening.png' AS BINARY),
    b.hash = CAST('5356af75e6dfe714fe72ea7113990c05d281b1c93246aba1f0c5b1332145f16a' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Jaffna Fusion Music Evening';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('03-jaffna-fusion-music-evening.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/03-jaffna-fusion-music-evening.png' AS BINARY), CAST('5356af75e6dfe714fe72ea7113990c05d281b1c93246aba1f0c5b1332145f16a' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Jaffna Fusion Music Evening'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('04-galle-fort-jazz-weekend.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/04-galle-fort-jazz-weekend.png' AS BINARY),
    b.hash = CAST('fe65b563897fb7abc86261c49c8d8d900c0f8a35b5f95d583ab9ec3de6d14dd2' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Galle Fort Jazz Weekend';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('04-galle-fort-jazz-weekend.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/04-galle-fort-jazz-weekend.png' AS BINARY), CAST('fe65b563897fb7abc86261c49c8d8d900c0f8a35b5f95d583ab9ec3de6d14dd2' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Galle Fort Jazz Weekend'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('05-nelum-pokuna-indie-live.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/05-nelum-pokuna-indie-live.png' AS BINARY),
    b.hash = CAST('854e3fad2193602dd8dbed1c626722cc75a1d1a8cd873fff24cf61a43a631ee6' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Nelum Pokuna Indie Live';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('05-nelum-pokuna-indie-live.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/05-nelum-pokuna-indie-live.png' AS BINARY), CAST('854e3fad2193602dd8dbed1c626722cc75a1d1a8cd873fff24cf61a43a631ee6' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Nelum Pokuna Indie Live'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('06-colombo-cloud-native-summit.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/06-colombo-cloud-native-summit.png' AS BINARY),
    b.hash = CAST('878896f8f29e3e19ef30f0aa71d334970d1091feefaab587778a63d9a5dc89b0' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Colombo Cloud Native Summit';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('06-colombo-cloud-native-summit.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/06-colombo-cloud-native-summit.png' AS BINARY), CAST('878896f8f29e3e19ef30f0aa71d334970d1091feefaab587778a63d9a5dc89b0' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Colombo Cloud Native Summit'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('07-trace-startup-demo-day.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/07-trace-startup-demo-day.png' AS BINARY),
    b.hash = CAST('12c06ee17d1e607feafd81077a3e48ddc60642237f385b3f4cdd5678444189e9' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Trace Startup Demo Day';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('07-trace-startup-demo-day.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/07-trace-startup-demo-day.png' AS BINARY), CAST('12c06ee17d1e607feafd81077a3e48ddc60642237f385b3f4cdd5678444189e9' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Trace Startup Demo Day'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('08-malabe-ai-builders-workshop.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/08-malabe-ai-builders-workshop.png' AS BINARY),
    b.hash = CAST('979445a28e7ceaec21def973970db6d2909aa54113e06b712653b06b9866cb81' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Malabe AI Builders Workshop';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('08-malabe-ai-builders-workshop.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/08-malabe-ai-builders-workshop.png' AS BINARY), CAST('979445a28e7ceaec21def973970db6d2909aa54113e06b712653b06b9866cb81' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Malabe AI Builders Workshop'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('09-peradeniya-robotics-expo.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/09-peradeniya-robotics-expo.png' AS BINARY),
    b.hash = CAST('8aa01b23107ef58e5c08aa32e437d755b25bfe235d37783d5288ea28bb629ea4' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Peradeniya Robotics Expo';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('09-peradeniya-robotics-expo.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/09-peradeniya-robotics-expo.png' AS BINARY), CAST('8aa01b23107ef58e5c08aa32e437d755b25bfe235d37783d5288ea28bb629ea4' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Peradeniya Robotics Expo'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('10-galle-digital-creators-lab.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/10-galle-digital-creators-lab.png' AS BINARY),
    b.hash = CAST('1d4440ec97c07ace7824b85ec8c33bd338bd17f820a9900a7f20e92a323c9fa5' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Galle Digital Creators Lab';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('10-galle-digital-creators-lab.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/10-galle-digital-creators-lab.png' AS BINARY), CAST('1d4440ec97c07ace7824b85ec8c33bd338bd17f820a9900a7f20e92a323c9fa5' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Galle Digital Creators Lab'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('11-colombo-futsal-champions-cup.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/11-colombo-futsal-champions-cup.png' AS BINARY),
    b.hash = CAST('0e3bd34b893aedd6aae29013ca89c2eee643fa4e8ee4d20bacf9c110c9bbf54b' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Colombo Futsal Champions Cup';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('11-colombo-futsal-champions-cup.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/11-colombo-futsal-champions-cup.png' AS BINARY), CAST('0e3bd34b893aedd6aae29013ca89c2eee643fa4e8ee4d20bacf9c110c9bbf54b' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Colombo Futsal Champions Cup'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('12-racecourse-rugby-sevens.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/12-racecourse-rugby-sevens.png' AS BINARY),
    b.hash = CAST('13f9b1d8475fdecab3e5d0de053b93fbb0b96294420e10da5991af51425af43d' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Racecourse Rugby Sevens';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('12-racecourse-rugby-sevens.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/12-racecourse-rugby-sevens.png' AS BINARY), CAST('13f9b1d8475fdecab3e5d0de053b93fbb0b96294420e10da5991af51425af43d' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Racecourse Rugby Sevens'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('13-kandy-hill-country-cricket-bash.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/13-kandy-hill-country-cricket-bash.png' AS BINARY),
    b.hash = CAST('d6483e80c6127f5629a9c664d4f1172e86f5494e0348a6067154d941da4625df' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Kandy Hill Country Cricket Bash';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('13-kandy-hill-country-cricket-bash.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/13-kandy-hill-country-cricket-bash.png' AS BINARY), CAST('d6483e80c6127f5629a9c664d4f1172e86f5494e0348a6067154d941da4625df' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Kandy Hill Country Cricket Bash'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('14-dambulla-cycling-challenge.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/14-dambulla-cycling-challenge.png' AS BINARY),
    b.hash = CAST('c428e83f9efa6b806a5bb4c89dac67a2ebc335a3fd4553f3ebafb6e39b4e0cb7' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Dambulla Cycling Challenge';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('14-dambulla-cycling-challenge.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/14-dambulla-cycling-challenge.png' AS BINARY), CAST('c428e83f9efa6b806a5bb4c89dac67a2ebc335a3fd4553f3ebafb6e39b4e0cb7' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Dambulla Cycling Challenge'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('15-matara-beach-volleyball-open.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/15-matara-beach-volleyball-open.png' AS BINARY),
    b.hash = CAST('a7e8e6978e0063bdd183f0cbdce11e50f1920bb447e98a6144648e7bdb7791aa' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Matara Beach Volleyball Open';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('15-matara-beach-volleyball-open.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/15-matara-beach-volleyball-open.png' AS BINARY), CAST('a7e8e6978e0063bdd183f0cbdce11e50f1920bb447e98a6144648e7bdb7791aa' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Matara Beach Volleyball Open'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('16-colombo-contemporary-canvas.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/16-colombo-contemporary-canvas.png' AS BINARY),
    b.hash = CAST('f4e5529bdf3144a5a35402c45e4bc968eccb47b17e3d63ede209803f70dc79a5' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Colombo Contemporary Canvas';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('16-colombo-contemporary-canvas.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/16-colombo-contemporary-canvas.png' AS BINARY), CAST('f4e5529bdf3144a5a35402c45e4bc968eccb47b17e3d63ede209803f70dc79a5' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Colombo Contemporary Canvas'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('17-barefoot-handmade-weekend.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/17-barefoot-handmade-weekend.png' AS BINARY),
    b.hash = CAST('74dce01156840e5e7d120f14d768de28516ed6488a51bc3e690e9a3e57f50c7b' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Barefoot Handmade Weekend';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('17-barefoot-handmade-weekend.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/17-barefoot-handmade-weekend.png' AS BINARY), CAST('74dce01156840e5e7d120f14d768de28516ed6488a51bc3e690e9a3e57f50c7b' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Barefoot Handmade Weekend'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('18-kandy-heritage-craft-fair.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/18-kandy-heritage-craft-fair.png' AS BINARY),
    b.hash = CAST('d0c15000a549a720da9f3630b6cec36c1ec3779764f1dab31cc9252cadb9fa0f' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Kandy Heritage Craft Fair';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('18-kandy-heritage-craft-fair.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/18-kandy-heritage-craft-fair.png' AS BINARY), CAST('d0c15000a549a720da9f3630b6cec36c1ec3779764f1dab31cc9252cadb9fa0f' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Kandy Heritage Craft Fair'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('19-jaffna-mural-stories.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/19-jaffna-mural-stories.png' AS BINARY),
    b.hash = CAST('844320e5ccefb9bc1f5931e90f164dbbd978fe5c3eab76030277565157a98dd2' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Jaffna Mural Stories';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('19-jaffna-mural-stories.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/19-jaffna-mural-stories.png' AS BINARY), CAST('844320e5ccefb9bc1f5931e90f164dbbd978fe5c3eab76030277565157a98dd2' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Jaffna Mural Stories'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('20-galle-fort-art-walk.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/20-galle-fort-art-walk.png' AS BINARY),
    b.hash = CAST('d6ecb8ec5651d470afb3f0ab4c7e3632e48243dfb9e972654c016cf0cb6ad8b8' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Galle Fort Art Walk';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('20-galle-fort-art-walk.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/20-galle-fort-art-walk.png' AS BINARY), CAST('d6ecb8ec5651d470afb3f0ab4c7e3632e48243dfb9e972654c016cf0cb6ad8b8' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Galle Fort Art Walk'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('21-colombo-street-food-fiesta.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/21-colombo-street-food-fiesta.png' AS BINARY),
    b.hash = CAST('a496e76aa7f0e203957bcec631140c6a4d4b4f415f27342e15c243fd2f91403c' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Colombo Street Food Fiesta';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('21-colombo-street-food-fiesta.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/21-colombo-street-food-fiesta.png' AS BINARY), CAST('a496e76aa7f0e203957bcec631140c6a4d4b4f415f27342e15c243fd2f91403c' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Colombo Street Food Fiesta'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('22-kandy-spice-trail-experience.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/22-kandy-spice-trail-experience.png' AS BINARY),
    b.hash = CAST('f3cc8d54000e3d21a2c727881e3ef50d25392e516cf94690c5c05c0af067af4e' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Kandy Spice Trail Experience';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('22-kandy-spice-trail-experience.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/22-kandy-spice-trail-experience.png' AS BINARY), CAST('f3cc8d54000e3d21a2c727881e3ef50d25392e516cf94690c5c05c0af067af4e' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Kandy Spice Trail Experience'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('23-galle-seafood-sundown.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/23-galle-seafood-sundown.png' AS BINARY),
    b.hash = CAST('874284afb25961c0d7978b33bed8cf931d9aa00bd096538de9da42bf09fc079c' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Galle Seafood Sundown';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('23-galle-seafood-sundown.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/23-galle-seafood-sundown.png' AS BINARY), CAST('874284afb25961c0d7978b33bed8cf931d9aa00bd096538de9da42bf09fc079c' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Galle Seafood Sundown'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('24-jaffna-taste-market.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/24-jaffna-taste-market.png' AS BINARY),
    b.hash = CAST('3ae347e604600d3bbbb5cf5be3a3057aec5c541033f708ea71ba8e0a505d6414' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Jaffna Taste Market';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('24-jaffna-taste-market.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/24-jaffna-taste-market.png' AS BINARY), CAST('3ae347e604600d3bbbb5cf5be3a3057aec5c541033f708ea71ba8e0a505d6414' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Jaffna Taste Market'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('25-negombo-lagoon-food-fest.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/25-negombo-lagoon-food-fest.png' AS BINARY),
    b.hash = CAST('39d427f3ff6f65d7b2f3355ddd70cf5cf229de11a41c32a8abeadb08d3dbf692' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Negombo Lagoon Food Fest';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('25-negombo-lagoon-food-fest.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/25-negombo-lagoon-food-fest.png' AS BINARY), CAST('39d427f3ff6f65d7b2f3355ddd70cf5cf229de11a41c32a8abeadb08d3dbf692' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Negombo Lagoon Food Fest'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('26-colombo-friday-laughs.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/26-colombo-friday-laughs.png' AS BINARY),
    b.hash = CAST('a11c526ae19184b05705133aaab207ee863fca20c000f7e4b8cad24ac8ec8910' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Colombo Friday Laughs';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('26-colombo-friday-laughs.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/26-colombo-friday-laughs.png' AS BINARY), CAST('a11c526ae19184b05705133aaab207ee863fca20c000f7e4b8cad24ac8ec8910' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Colombo Friday Laughs'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('27-kandy-punchline-night.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/27-kandy-punchline-night.png' AS BINARY),
    b.hash = CAST('243d7ebb881e568b640c7bac4ed5f66997b1147abebf0c7b0d8540d9c715b224' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Kandy Punchline Night';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('27-kandy-punchline-night.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/27-kandy-punchline-night.png' AS BINARY), CAST('243d7ebb881e568b640c7bac4ed5f66997b1147abebf0c7b0d8540d9c715b224' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Kandy Punchline Night'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('28-galle-fort-comedy-roast.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/28-galle-fort-comedy-roast.png' AS BINARY),
    b.hash = CAST('e4d4fad2d7aef914e2ef5bc1f24ec0357dc8734738efb87ebb92b005afcb10d1' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Galle Fort Comedy Roast';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('28-galle-fort-comedy-roast.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/28-galle-fort-comedy-roast.png' AS BINARY), CAST('e4d4fad2d7aef914e2ef5bc1f24ec0357dc8734738efb87ebb92b005afcb10d1' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Galle Fort Comedy Roast'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('29-jaffna-open-mic-comedy.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/29-jaffna-open-mic-comedy.png' AS BINARY),
    b.hash = CAST('b997deec2078843febdc882a83eac7d78ed28a9b174756586b3475a5cb8c5194' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Jaffna Open Mic Comedy';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('29-jaffna-open-mic-comedy.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/29-jaffna-open-mic-comedy.png' AS BINARY), CAST('b997deec2078843febdc882a83eac7d78ed28a9b174756586b3475a5cb8c5194' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Jaffna Open Mic Comedy'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

UPDATE event_banner b JOIN events e ON b.event_id = e.id
SET b.directory = CAST('ec7205-event-booking/banner/' AS BINARY),
    b.file_name = CAST('30-negombo-weekend-comedy-club.png' AS BINARY),
    b.resource_url = CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/30-negombo-weekend-comedy-club.png' AS BINARY),
    b.hash = CAST('17adddb738ce1759524d4b824528577912fc53015f96aa8291c35f94f45c4bf3' AS BINARY),
    b.created_date = NOW()
WHERE e.title = 'Negombo Weekend Comedy Club';

INSERT INTO event_banner (property_id, directory, file_name, resource_url, hash, created_date, event_id)
SELECT CONCAT('seed-banner-', e.id), CAST('ec7205-event-booking/banner/' AS BINARY), CAST('30-negombo-weekend-comedy-club.png' AS BINARY), CAST('https://my-files-amiru.s3.amazonaws.com/ec7205-event-booking/banner/30-negombo-weekend-comedy-club.png' AS BINARY), CAST('17adddb738ce1759524d4b824528577912fc53015f96aa8291c35f94f45c4bf3' AS BINARY), NOW(), e.id
FROM events e WHERE e.title = 'Negombo Weekend Comedy Club'
AND NOT EXISTS (SELECT 1 FROM event_banner b WHERE b.event_id = e.id);

-- END GENERATED EVENT BANNER SEED
