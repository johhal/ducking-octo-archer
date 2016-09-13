enum directions {up = 0, down = 1, left = 2, right = 3};

var vision = argument[0];
var pos_x = argument[1];
var pos_y = argument[2];
var modifier = argument[3];

var grid_pos_x = pos_x/obj_grassland.sprite_width;
var grid_pos_y = pos_y/obj_grassland.sprite_height;

var i = 0;
var j = 0;
var k = 0;


//for(i = 0; i < vision; i++)
//{
    for(j = -vision; j <= vision; j++) //8 kommer inte funka för större vision.... 
    {
        for(k = -vision; k <= vision; k++){
            global.world_map[# grid_pos_x + j, grid_pos_y + k].units_with_vision += modifier;
            if(global.world_map[# grid_pos_x + j, grid_pos_y + k].units_with_vision != 0)
            {
                global.world_map[# grid_pos_x + j, grid_pos_y + k].depth = 10;
                global.world_map[# grid_pos_x + j, grid_pos_y + k].image_index = 1;
            }
            else
            {    
                global.world_map[# grid_pos_x + j, grid_pos_y + k].depth = 0;
                global.world_map[# grid_pos_x + j, grid_pos_y + k].image_index = 2;
            }
        }
   }
//}
