package com.example.turfnow.util

class Constants {
    companion object{
        const val INITIAL_TURF_QUERY="""INSERT INTO turf (name, location, ratings, image_url)
        VALUES
        ('Rush', 'Chennai', "4.5", 'https://assets.telegraphindia.com/telegraph/2021/Sep/1630669298_sky-turf.jpg'),
        ('MGG Sports Club', 'Chennai', "3.5", 'https://cricketgraph.com/wp-content/gallery/enc-sports-turf/ENC-Sports-Turf-Thane-West-8.jpg'),
        ('Urban', 'Delhi', "4", 'https://media.hudle.in/venues/2ddcfd74-cd9c-4221-9bcf-21e8e9ac8f1b/photo/e6a0f89d6f84a62db2ff94f6ec2ee3227e24f804'),
        ('Football Areana', 'Bengaluru', "4.5", 'https://media.istockphoto.com/id/983979694/photo/corner-line-of-an-indoor-football-soccer-training-field.jpg?s=612x612&w=0&k=20&c=1LljSla_DxIqNulQtAap1OAndN9KGD_gb67SZy4sh8A='),
        ('Tricshot', 'Mumbai', "4.5", 'https://cpimg.tistatic.com/04924175/b/4/Multi-Sport-Turf.jpg'),
        ('Green Box', 'Pune', "4.5", 'https://2.wlimg.com/product_images/bc-full/2022/5/9649280/sports-turf-setup-services-1652508281-6233844.jpeg'),
        ('Player Sports', 'Surat', "4.5", 'https://res.cloudinary.com/sporthood/image/fetch/t_xlarge/https://api.sporthood.in/media/venue/WhatsApp_Image_2022-07-01_at_5.08.52_PM.jpeg')"""
        const val INITIAL_CATEGORY_QUERY="INSERT INTO category (name, image_url)\n" +
                "VALUES \n" +
                "   ('Cricket','https://img.freepik.com/premium-vector/cricket-player-logo-design-vector-icon-symbol-template-illustration_647432-117.jpg'),\n" +
                "   ('Football','https://img.freepik.com/premium-vector/art-illustration-logo-sport-design-icon-football-symbol-men-soccer-when-action-kicking-ball_678437-207.jpg?w=2000'),\n" +
                "   ('Tennis','https://static.vecteezy.com/system/resources/previews/005/656/251/original/racket-with-ball-flat-design-of-tennis-icon-vector.jpg'),\n" +
                "   ('Table Tennis','https://img.freepik.com/premium-vector/table-tennis-rackets-game-is-ping-pong-vector-illustration-flat-design-isolated-white-background-sports-icon_153097-3065.jpg'),\n" +
                "   ('Basket Ball','https://st2.depositphotos.com/1008406/8635/v/950/depositphotos_86358160-stock-illustration-basketball-icon.jpg');"

        const val INITIAL_GROUND_TYPE_QUERY="Insert into ground_type (turf_id,category_id,capacity) values \n" +
                "(1,1,10),\n" +
                "(1,3,12),\n" +
                "(1,5,15),\n" +
                "(2,1,13),\n" +
                "(2,2,15),\n" +
                "(2,4,10),\n" +
                "(2,5,17),\n" +
                "(3,2,23),\n" +
                "(3,3,24),\n" +
                "(3,5,12),\n" +
                "(4,1,10),\n" +
                "(4,2,20),\n" +
                "(4,5,30),\n" +
                "(5,5,10),\n" +
                "(5,1,25),\n" +
                "(5,2,18),\n" +
                "(6,4,12),\n" +
                "(6,2,10),\n" +
                "(6,1,20),\n" +
                "(7,3,32),\n" +
                "(7,5,5);"
        const val AVAILABILE_SLOTS ="""INSERT INTO availability (ground_type_id, date, slot_time, is_booked, price)
VALUES 
    (1, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (1, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (1, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (1, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (1, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (1, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (1, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (1, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (1, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (1, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (1, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (1, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (1, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (1, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (1, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    (2, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (2, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (2, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (2, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (2, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (2, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (2, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (2, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (2, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (2, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (2, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (2, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (2, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (2, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (2, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    
    
     (3, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (3, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (3, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (3, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (3, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (3, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (3, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (3, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (3, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (3, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (3, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (3, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (3, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (3, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (3, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    
    
    
    (4, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (4, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (4, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (4, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (4, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (4, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (4, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (4, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (4, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (4, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (4, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (4, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (4, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (4, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (4, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    
    
    
    (5, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (5, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (5, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (5, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (5, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (5, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (5, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (5, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (5, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (5, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (5, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (5, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (5, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (5, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (5, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    
    
    
    (6, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (6, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (6, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (6, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (6, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (6, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (6, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (6, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (6, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (6, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (6, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (6, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (6, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (6, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (6, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    
    
     (7, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (7, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (7, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (7, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (7, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (7, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (7, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (7, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (7, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (7, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (7, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (7, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (7, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (7, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (7, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200),
    
    
    
    (8, strftime('%d-%m-%Y', date('now')), '08:55-09:55', 0, 1000),
    (8, strftime('%d-%m-%Y', date('now', '+1 day')), '08:55-09:55', 0, 1000),
    (8, strftime('%d-%m-%Y', date('now', '+2 day')), '08:55-09:55', 0, 1000),
    (8, strftime('%d-%m-%Y', date('now', '+3 day')), '08:55-09:55', 0, 1000),
    (8, strftime('%d-%m-%Y', date('now', '+4 day')), '08:55-09:55', 0, 1000),
    (8, strftime('%d-%m-%Y', date('now')), '11:55-12:55', 0, 900),
    (8, strftime('%d-%m-%Y', date('now', '+1 day')),'11:55-12:55', 0, 900),
    (8, strftime('%d-%m-%Y', date('now', '+2 day')), '11:55-12:55', 0, 900),
    (8, strftime('%d-%m-%Y', date('now', '+3 day')), '11:55-12:55', 0, 900),
    (8, strftime('%d-%m-%Y', date('now', '+4 day')), '11:55-12:55', 0, 900),
    (8, strftime('%d-%m-%Y', date('now')), '17:55-20:55', 0, 1200),
    (8, strftime('%d-%m-%Y', date('now', '+1 day')),'17:55-20:55', 0, 1200),
    (8, strftime('%d-%m-%Y', date('now', '+2 day')), '17:55-20:55', 0, 1200),
    (8, strftime('%d-%m-%Y', date('now', '+3 day')), '17:55-20:55', 0, 1200),
    (8, strftime('%d-%m-%Y', date('now', '+4 day')), '17:55-20:55', 0, 1200);
"""
    }
}