package bo.custom.impl;

import bo.custom.ItemBo;
import bo.custom.PartBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.custom.PartDao;
import dao.util.DaoType;
import dto.ItemDto;
import dto.PartDto;
import entity.Item;
import entity.Part;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartBoImpl implements PartBo {
    private PartDao partDao = DaoFactory.getInstance().getDao(DaoType.PART);
    @Override
    public boolean savePart(PartDto dto) throws SQLException, ClassNotFoundException {
        return partDao.save(new Part(
                dto.getPartId(),
                dto.getPartName(),
                dto.getUnitPrice()
        ));
    }

    @Override
    public boolean updatePart(PartDto dto) throws SQLException, ClassNotFoundException {
        return partDao.update(new Part(
                dto.getPartId(),
                dto.getPartName(),
                dto.getUnitPrice()
        ));
    }

    @Override
    public boolean deletePart(String id) throws SQLException, ClassNotFoundException {
        return partDao.delete(id);
    }

    @Override
    public List<PartDto> allParts() throws SQLException, ClassNotFoundException {
        List<Part> entityList = partDao.getAll();
        List<PartDto> list = new ArrayList<>();
        for (Part part:entityList) {
            list.add(new PartDto(
                    part.getPartId(),
                    part.getPartName(),
                    part.getUnitPrice()
            ));
        }
        return list;
    }

    @Override
    public PartDto getPart(String id) throws SQLException, ClassNotFoundException {
        Part part = partDao.getPart(id);

        return (new PartDto(
                part.getPartId(),
                part.getPartName(),
                part.getUnitPrice()
        ));
    }
}
