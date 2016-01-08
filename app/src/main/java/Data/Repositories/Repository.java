package Data.Repositories;

import java.util.ArrayList;
import java.util.Collections;

import models.DataEntity;

/**
 * Created by Adam on 2015-11-24.
 */
public class Repository
{
    protected ArrayList<DataEntity> entities;

    public Repository(ArrayList<DataEntity> inputData)
    {
        entities = inputData;
    }
    
    public DataEntity getElementById(final int id)
    {
        for (DataEntity entity :
                entities) {
            if (entity.getId() == id)
                return entity;
        }
        return null;
    }
}
