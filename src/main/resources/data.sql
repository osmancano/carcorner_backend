INSERT INTO public.car_model (id,name) VALUES (-1,'Camery');
INSERT INTO public.car_model (id,name) VALUES (-2,'Corolla');
INSERT INTO public.car_model (id,name) VALUES (-3,'Yaris');
INSERT INTO public.car_model (id,name) VALUES (-4,'Camery');
INSERT INTO public.car_model (id,name) VALUES (-5,'Click');
INSERT INTO public.car_model (id,name) VALUES (-6,'Sonata');
INSERT INTO public.car_model (id,name) VALUES (-7,'Getz');

INSERT INTO public.car_make (id, name) VALUES (-1, 'Toyota');
INSERT INTO public.car_make (id, name) VALUES (-2, 'Hyundai');

INSERT INTO public.car_make_models VALUES (-1, -1);
INSERT INTO public.car_make_models VALUES (-1, -2);
INSERT INTO public.car_make_models VALUES (-1, -3);
INSERT INTO public.car_make_models VALUES (-1, -4);
INSERT INTO public.car_make_models VALUES (-2, -5);
INSERT INTO public.car_make_models VALUES (-2, -6);
INSERT INTO public.car_make_models VALUES (-2, -7);

INSERT INTO public.car_user (id, address, email, firstname, image, lastname, password, phone) VALUES (-1, '151 summit tower blvd', 'canohibro@gmail.com', 'Osman', '1490191818528_Osman.jpeg', 'Idris', 'nothing', '+15124001387');

INSERT INTO public.car (id, address, description, image, is_available, latitude, longitude, make, model, price_per_day, transmission, year, owner_id) VALUES (-1, '10 pennsylvania ave, washington DC', 'Very Low Cost Car', '1490188538219_yaris2008.jpeg', true, '28.541076699999998', '-81.38117419999999', 'Toyota', 'Yaris', 45, 'Manual', 2008, -1);
INSERT INTO public.car (id, address, description, image, is_available, latitude, longitude, make, model, price_per_day, transmission, year, owner_id) VALUES (-2, '151, Summit Tower Blvd 32810', 'Low Cost Car', '1490189249460_corolla2007.jpeg', true, '28.541076699999998', '-81.38117419999999', 'Toyota', 'Corolla', 65, 'Automatic', 2007, -1);