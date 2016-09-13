//Funkar tydligen inte som tänkt! :/
enum class_name {peasant = 0, 
                    bard = 1};

nr_of_classes = 1;

nr = irandom(20);
tmp = class_name.peasant;
if(nr == 1)
{
    return irandom(nr_of_classes) + 1;
}
return tmp;

