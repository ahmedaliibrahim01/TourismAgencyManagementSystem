PGDMP  1    0                |            tourismagencysystem    16.2    16.2 1    C           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            D           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            E           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            F           1262    57413    tourismagencysystem    DATABASE     u   CREATE DATABASE tourismagencysystem WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'C';
 #   DROP DATABASE tourismagencysystem;
                postgres    false            �            1259    73925    facility    TABLE     v   CREATE TABLE public.facility (
    facility_id integer NOT NULL,
    facility_name character varying(100) NOT NULL
);
    DROP TABLE public.facility;
       public         heap    postgres    false            �            1259    73924    facility_facility_id_seq    SEQUENCE     �   CREATE SEQUENCE public.facility_facility_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.facility_facility_id_seq;
       public          postgres    false    218            G           0    0    facility_facility_id_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.facility_facility_id_seq OWNED BY public.facility.facility_id;
          public          postgres    false    217            �            1259    73953    hotel    TABLE     �  CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_name character varying(100) NOT NULL,
    hotel_city character varying(100) NOT NULL,
    hotel_region character varying(100) NOT NULL,
    hotel_full_address character varying(300) NOT NULL,
    hotel_phone character varying(20) NOT NULL,
    hotel_email character varying(100) NOT NULL,
    hotel_star character varying(10) NOT NULL,
    hotel_facilities text[] NOT NULL,
    hotel_pension_types text[]
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    73952    hotel1_hotel_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hotel1_hotel_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.hotel1_hotel_id_seq;
       public          postgres    false    220            H           0    0    hotel1_hotel_id_seq    SEQUENCE OWNED BY     J   ALTER SEQUENCE public.hotel1_hotel_id_seq OWNED BY public.hotel.hotel_id;
          public          postgres    false    219            �            1259    90749    hotel_pension    TABLE     �   CREATE TABLE public.hotel_pension (
    pension_id bigint NOT NULL,
    hotel_id integer NOT NULL,
    pension_type character varying(300) NOT NULL
);
 !   DROP TABLE public.hotel_pension;
       public         heap    postgres    false            �            1259    90754    hotel_pension_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_pension ALTER COLUMN pension_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    91093 
   hotel_room    TABLE     R  CREATE TABLE public.hotel_room (
    room_id integer NOT NULL,
    hotel_name character varying NOT NULL,
    type character varying NOT NULL,
    stock integer NOT NULL,
    adult_price double precision NOT NULL,
    child_price double precision NOT NULL,
    bed_capacity integer NOT NULL,
    square_meter integer NOT NULL,
    television boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    cash_box boolean NOT NULL,
    projection boolean NOT NULL,
    hotel_id integer,
    pension_id integer,
    season_id integer,
    hotel_city character varying
);
    DROP TABLE public.hotel_room;
       public         heap    postgres    false            �            1259    91092    hotel_room1_room_id_seq    SEQUENCE     �   CREATE SEQUENCE public.hotel_room1_room_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public.hotel_room1_room_id_seq;
       public          postgres    false    226            I           0    0    hotel_room1_room_id_seq    SEQUENCE OWNED BY     R   ALTER SEQUENCE public.hotel_room1_room_id_seq OWNED BY public.hotel_room.room_id;
          public          postgres    false    225            �            1259    90757    hotel_season    TABLE     �   CREATE TABLE public.hotel_season (
    season_id bigint NOT NULL,
    hotel_id integer NOT NULL,
    start_date date NOT NULL,
    finish_date date NOT NULL
);
     DROP TABLE public.hotel_season;
       public         heap    postgres    false            �            1259    90760    hotel_season_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_season ALTER COLUMN season_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    223            �            1259    91102    reservation    TABLE       CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    room_id integer NOT NULL,
    guest_name character varying(100) NOT NULL,
    guest_id character varying(100) NOT NULL,
    guest_email character varying(100) NOT NULL,
    guest_phone character varying(100) NOT NULL,
    adult_count integer NOT NULL,
    child_count integer NOT NULL,
    check_in_date date NOT NULL,
    check_out_date date NOT NULL,
    guest_note character varying(255),
    total_price integer NOT NULL,
    hotel_id integer
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    91101    reservation_reservation_id_seq    SEQUENCE     �   CREATE SEQUENCE public.reservation_reservation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 5   DROP SEQUENCE public.reservation_reservation_id_seq;
       public          postgres    false    228            J           0    0    reservation_reservation_id_seq    SEQUENCE OWNED BY     a   ALTER SEQUENCE public.reservation_reservation_id_seq OWNED BY public.reservation.reservation_id;
          public          postgres    false    227            �            1259    57414    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_name_surname character varying(100),
    user_user character varying(100),
    user_password character varying(100),
    user_role character varying(20)
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    57417    user_user_id_seq    SEQUENCE     �   CREATE SEQUENCE public.user_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.user_user_id_seq;
       public          postgres    false    215            K           0    0    user_user_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.user_user_id_seq OWNED BY public."user".user_id;
          public          postgres    false    216            �           2604    73928    facility facility_id    DEFAULT     |   ALTER TABLE ONLY public.facility ALTER COLUMN facility_id SET DEFAULT nextval('public.facility_facility_id_seq'::regclass);
 C   ALTER TABLE public.facility ALTER COLUMN facility_id DROP DEFAULT;
       public          postgres    false    217    218    218            �           2604    73956    hotel hotel_id    DEFAULT     q   ALTER TABLE ONLY public.hotel ALTER COLUMN hotel_id SET DEFAULT nextval('public.hotel1_hotel_id_seq'::regclass);
 =   ALTER TABLE public.hotel ALTER COLUMN hotel_id DROP DEFAULT;
       public          postgres    false    220    219    220            �           2604    91096    hotel_room room_id    DEFAULT     y   ALTER TABLE ONLY public.hotel_room ALTER COLUMN room_id SET DEFAULT nextval('public.hotel_room1_room_id_seq'::regclass);
 A   ALTER TABLE public.hotel_room ALTER COLUMN room_id DROP DEFAULT;
       public          postgres    false    225    226    226            �           2604    91105    reservation reservation_id    DEFAULT     �   ALTER TABLE ONLY public.reservation ALTER COLUMN reservation_id SET DEFAULT nextval('public.reservation_reservation_id_seq'::regclass);
 I   ALTER TABLE public.reservation ALTER COLUMN reservation_id DROP DEFAULT;
       public          postgres    false    227    228    228            �           2604    57418    user user_id    DEFAULT     n   ALTER TABLE ONLY public."user" ALTER COLUMN user_id SET DEFAULT nextval('public.user_user_id_seq'::regclass);
 =   ALTER TABLE public."user" ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    216    215            6          0    73925    facility 
   TABLE DATA           >   COPY public.facility (facility_id, facility_name) FROM stdin;
    public          postgres    false    218   �;       8          0    73953    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_name, hotel_city, hotel_region, hotel_full_address, hotel_phone, hotel_email, hotel_star, hotel_facilities, hotel_pension_types) FROM stdin;
    public          postgres    false    220   c<       9          0    90749    hotel_pension 
   TABLE DATA           K   COPY public.hotel_pension (pension_id, hotel_id, pension_type) FROM stdin;
    public          postgres    false    221   =       >          0    91093 
   hotel_room 
   TABLE DATA           �   COPY public.hotel_room (room_id, hotel_name, type, stock, adult_price, child_price, bed_capacity, square_meter, television, minibar, game_console, cash_box, projection, hotel_id, pension_id, season_id, hotel_city) FROM stdin;
    public          postgres    false    226   c=       ;          0    90757    hotel_season 
   TABLE DATA           T   COPY public.hotel_season (season_id, hotel_id, start_date, finish_date) FROM stdin;
    public          postgres    false    223   �=       @          0    91102    reservation 
   TABLE DATA           �   COPY public.reservation (reservation_id, room_id, guest_name, guest_id, guest_email, guest_phone, adult_count, child_count, check_in_date, check_out_date, guest_note, total_price, hotel_id) FROM stdin;
    public          postgres    false    228   >       3          0    57414    user 
   TABLE DATA           a   COPY public."user" (user_id, user_name_surname, user_user, user_password, user_role) FROM stdin;
    public          postgres    false    215   e>       L           0    0    facility_facility_id_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.facility_facility_id_seq', 10, true);
          public          postgres    false    217            M           0    0    hotel1_hotel_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.hotel1_hotel_id_seq', 71, true);
          public          postgres    false    219            N           0    0    hotel_pension_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.hotel_pension_id_seq', 34, true);
          public          postgres    false    222            O           0    0    hotel_room1_room_id_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.hotel_room1_room_id_seq', 17, true);
          public          postgres    false    225            P           0    0    hotel_season_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.hotel_season_id_seq', 34, true);
          public          postgres    false    224            Q           0    0    reservation_reservation_id_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 24, true);
          public          postgres    false    227            R           0    0    user_user_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.user_user_id_seq', 22, true);
          public          postgres    false    216            �           2606    73930    facility facility_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.facility
    ADD CONSTRAINT facility_pkey PRIMARY KEY (facility_id);
 @   ALTER TABLE ONLY public.facility DROP CONSTRAINT facility_pkey;
       public            postgres    false    218            �           2606    73960    hotel hotel1_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel1_pkey PRIMARY KEY (hotel_id);
 ;   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel1_pkey;
       public            postgres    false    220            �           2606    90756     hotel_pension hotel_pension_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.hotel_pension
    ADD CONSTRAINT hotel_pension_pkey PRIMARY KEY (pension_id);
 J   ALTER TABLE ONLY public.hotel_pension DROP CONSTRAINT hotel_pension_pkey;
       public            postgres    false    221            �           2606    91100    hotel_room hotel_room1_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.hotel_room
    ADD CONSTRAINT hotel_room1_pkey PRIMARY KEY (room_id);
 E   ALTER TABLE ONLY public.hotel_room DROP CONSTRAINT hotel_room1_pkey;
       public            postgres    false    226            �           2606    90762    hotel_season hotel_season_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.hotel_season
    ADD CONSTRAINT hotel_season_pkey PRIMARY KEY (season_id);
 H   ALTER TABLE ONLY public.hotel_season DROP CONSTRAINT hotel_season_pkey;
       public            postgres    false    223            �           2606    91109    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    228            �           2606    57420    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    215            6   \   x���@0��ݯ� �������ȋ�DU���wNI}dV�d�@�}�{965������0dO��&����;�\DKm���B��r|de,9�j�      8   �   x�37�<�~d���E�y
�9�ɩ
ީ��y�ٙ���%�yI�9��E%�ه�U"�vNLII-��Q�˷26�Q0616����K�/r�������˩mi`dhdldf�����J�噹��y�
��9J:JnE��
ᙺn�J:����1~\1z\\\ �65�      9   F   x�36�47��))JTp��Q��K�)-�,K�26ɸ���RS2KR+�r)�y�
�9���9\1z\\\ �l�      >   ]   x�34�<�~d���E�y
�9�ɩ
ީ��y�ٙ���y�9�
A����&����@l�ihd���Y� �܀���<�K�Js�b���� �6�      ;   -   x�36�47�4202�50�50�0��L.c��!\��+F��� AR
*      @   H   x�32�44�t��MMQp���0��D��Cnbf�^r~.�)p����������	�i��i�gn����� Σ-      3   ?   x�3�t��MMQp���LL����442�tt����2B�K�-�ɯLMK����G��r��qqq |��     