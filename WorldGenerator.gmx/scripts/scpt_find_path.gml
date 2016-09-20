var width = global.world_width;
var height = global.world_height;

data_x[0] = 1;
data_x[1] = 0;
data_x[2] = -1;
data_x[3] = 0;

data_y[0] = 0;
data_y[1] = 1;
data_y[2] = 0;
data_y[3] = -1;

var px = xpos_grid;
var py = ypos_grid;

var done = 0;

var object_counter = 1;
var current_counter = 0;  

list[0, 0] = px
list[0, 1] = py
list[0, 2] = -1
//list[0, 3] = -1

grid_visited = ds_grid_create(width, height);
ds_grid_clear(grid_visited, 0);
while(done != 1 && object_counter > current_counter)
{
    for(var i = 0; i < 4; i ++)
    {
        current_pos_x = list[current_counter, 0] + data_x[i];
        current_pos_y = list[current_counter, 1] + data_y[i];   
        
        //om inne i nätet?;
        if(scpt_inside_world(current_pos_x, current_pos_y))
        {
            //i mål?
            if(current_pos_x == target_x && current_pos_y == target_y)
            {
                path_found = true;
                //avsluta, tills vidare gör inget.
                show_debug_message("test done");
                done = 1;
                //target_x = -1;
                var j = 0;
                var prev_x = current_pos_x;
                var prev_y = current_pos_y;
                current = current_counter;
                tmp_list[0] = 0;
                while(current != -1)
                {
                    current_pos_x = list[current, 0];
                    current_pos_y = list[current, 1];
                    var x_val = current_pos_x - prev_x;
                    var y_val = current_pos_y - prev_y;
                    var tmp = x_val * 2 + y_val;
                    
                    switch(tmp)
                    {
                        case -2:
                            tmp_list[j++] = directions.right;
                            break;
                        case -1:
                            tmp_list[j++] = directions.down;
                            break;
                        case 1:
                            tmp_list[j++] = directions.up;
                            break;
                        case 2:
                            tmp_list[j++] = directions.left;
                            break;
                        default:
                            break;
                    }
                    var prev_x = current_pos_x;
                    var prev_y = current_pos_y;
                    current = list[current, 2];
                }
                max_move = j;
                for(j = 0; j < max_move; j++)
                    walk_list[j] = tmp_list[(max_move - 1) - j];
            }
            else
            {
                if(grid_visited[# current_pos_x, current_pos_y] != 1)
                {
                    grid_visited[# current_pos_x, current_pos_y] = 1;  
                    
                    if(global.world_map[#current_pos_x, current_pos_y].my_land_type == walk_permission)
                    {
                        list[object_counter, 0] = current_pos_x;
                        list[object_counter, 1] = current_pos_y;
                        list[object_counter++, 2] = current_counter;
                    }
                }    
            }    
        }    
    }
    current_counter++;
}
if(done != 1){
    show_debug_message("NONONONONONO");
    object_counter = current_counter;  
    target_x = -1;
    target_y = -1;
}

