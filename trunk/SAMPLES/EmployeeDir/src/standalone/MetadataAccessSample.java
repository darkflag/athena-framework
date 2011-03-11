/*
 * $Id$
 * Copyright (c) 2008-2011 Insprise Software (Shanghai) Co. Ltd.
 * All Rights Reserved
 * Changelog:
 *   Jack - 30-Jan-2011: Initial version
 *
 */

package standalone;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.athenasource.framework.eo.core.EOConfiguration;
import org.athenasource.framework.eo.core.EOService;
import org.athenasource.framework.eo.core.IEntity;
import org.athenasource.framework.eo.core.MetaDomain;
import org.athenasource.framework.eo.core.baseclass.Attribute;
import org.athenasource.framework.eo.core.baseclass.Entity;
import org.athenasource.framework.eo.core.baseclass.Relationship;

public class MetadataAccessSample {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        EOService eoService = constructEOService();
        System.out.println("Entity count: " + eoService.getMetaDomain().getAllEntities(true).size());

        MetaDomain metaDomain = eoService.getMetaDomain();

        // Find
        Entity entity1 = (Entity) eoService.getMetaDomain().getEntity("Employee");
        //Entity entity2 = (Entity) eoService.getMetaDomain().getEntity(101);

        Attribute attribute1 = entity1.getAttributeBySystemName("nameFull");
        Attribute attribute2 = entity1.getAttribute(4);

        Relationship relationship = entity1.getRelationship("addresses");

        // reload.
        eoService.loadMetadomain(true);

        // List all entities.
        List<IEntity> entities = metaDomain.getAllEntities(false);
        for(IEntity ientity : entities) {
        	Entity entity = (Entity)ientity;
        	if(entity.isCoreEntity()) {
        		continue; // exclude system entities.
        	}
        	System.out.println("Entity: " + entity.getSystemName() + ", id: " + entity.getId());
        	List<Attribute> attributes = entity.getAttributes();
        	for(Attribute attribute : attributes) {
        		System.out.println("\tAttribute: " + attribute.getSystemName() + ", " + attribute.getTypeInfo());
        	}
        	List<Relationship> relationships = entity.getRelationships();
        	for(Relationship rel : relationships) {
        		System.out.println("\tRelationship: " + rel.getSystemName() + ", " +
        			rel.getSourceEntity().getSystemName() + "." + rel.getSourceAttribute().getSystemName() + "->" +
        			rel.getTargetEntity().getSystemName() + "." + rel.getTargetAttribute().getSystemName());
        	}
        }
    }

    public static EOService constructEOService() throws IOException {
    	EOConfiguration eoConfig = new EOConfiguration(new File("WebContent/WEB-INF/eo-config.xml").toURI().toString());
        EOService eoService = new EOService(eoConfig);
        eoService.loadMetadomain(); // only need to load once.
        return eoService;
    }



}
