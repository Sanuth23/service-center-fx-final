package bo.custom.impl;

import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.DaoType;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private ItemDao itemDao = DaoFactory.getInstance().getDao(DaoType.ITEM);
    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(
                dto.getCode(),
                dto.getName(),
                dto.getCategory(),
                dto.getServiceFee()
        ));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.update(new Item(
                dto.getCode(),
                dto.getName(),
                dto.getCategory(),
                dto.getServiceFee()
        ));
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDao.delete(code);
    }

    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        List<Item> entityList = itemDao.getAll();
        List<ItemDto> list = new ArrayList<>();
        for (Item item:entityList) {
            list.add(new ItemDto(
                    item.getCode(),
                    item.getName(),
                    item.getCategory(),
                    item.getServiceFee()
            ));
        }
        return list;
    }

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDao.getItem(code);

        return (new ItemDto(
                item.getCode(),
                item.getName(),
                item.getCategory(),
                item.getServiceFee()
        ));
    }
}
