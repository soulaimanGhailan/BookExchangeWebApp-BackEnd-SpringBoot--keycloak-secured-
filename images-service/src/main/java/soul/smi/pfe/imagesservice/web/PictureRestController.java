package soul.smi.pfe.imagesservice.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.imagesservice.dao.entities.Picture;
import soul.smi.pfe.imagesservice.dto.PictureDto;
import soul.smi.pfe.imagesservice.mapper.Mapper;
import soul.smi.pfe.imagesservice.service.PicturesService;

@RestController
@RequestMapping("pictures")
@AllArgsConstructor
public class PictureRestController {
    private PicturesService picturesService;
    private Mapper mapper;
    @GetMapping("{id}")
    public PictureDto getPic(@PathVariable Long id){
        return picturesService.getPicture(id);
    }
    @PostMapping("/createImage/{picName}")
    public PictureDto createPic(@RequestBody String imageBase64 , @PathVariable String picName){
        return mapper.fromPicture(picturesService.createPicFromBase64(picName , imageBase64));
    }
    @PutMapping("/updatePicture/{picId}/{picName}")
    public PictureDto updatePic(@RequestBody String imageBase64, @PathVariable Long picId , @PathVariable String picName){
        if(picId == 1){
            return mapper.fromPicture(picturesService.createPicFromBase64(picName , imageBase64));
        }
        return mapper.fromPicture(picturesService.upDatePicture(picId , imageBase64));
    }
    @PutMapping("/updateBookPicture/{picId}")
    public PictureDto updateBookPic(@RequestBody String imageContentBase64,@PathVariable Long picId){

        return mapper.fromPicture(picturesService.upDatePicture(picId , imageContentBase64));
    }
    @DeleteMapping("{id}")
    Picture deletePictureOfBook(@PathVariable Long id){
        return picturesService.deletePic(id);
    }
}
