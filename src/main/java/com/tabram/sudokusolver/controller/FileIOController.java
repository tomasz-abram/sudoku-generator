//package com.tabram.sudokusolver.controller;
//
//import com.tabram.sudokusolver.dto.FileBucket;
//import com.tabram.sudokusolver.model.SudokuBoardObject;
//import com.tabram.sudokusolver.repository.SudokuBoardRepository;
//import com.tabram.sudokusolver.service.FileIOService;
//import com.tabram.sudokusolver.validation.FileValid;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.validation.Valid;
//import java.io.IOException;
//
//@Controller
//@RequestMapping({"/", "/home"})
//public class FileIOController {
//
//    private static final String HOME = "redirect:/";
//
//    private final FileIOService fileIOService;
//
//    private final SudokuBoardRepository sudokuBoardRepository;
//
//    public FileIOController(FileIOService fileIOService, SudokuBoardRepository sudokuBoardRepository) {
//        this.fileIOService = fileIOService;
//        this.sudokuBoardRepository = sudokuBoardRepository;
//    }
//
//    @GetMapping("/download-file")
//    public ResponseEntity<byte[]> downloadFile() {
//        SudokuBoardObject sudokuBoardObject = sudokuBoardRepository.getSudokuBoardObject();
//        String sudokuJsonString = fileIOService.exportBoard(sudokuBoardObject);
//        byte[] sudokuJsonBytes = sudokuJsonString.getBytes();
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=sudokuBoard.json")
//                .contentType(MediaType.APPLICATION_JSON)
//                .contentLength(sudokuJsonBytes.length)
//                .body(sudokuJsonBytes);
//    }
//
//    @PostMapping("/upload-file")
//    public String uploadFile(@ModelAttribute("fileBucket")@Valid FileBucket fileBucket, BindingResult result,  Model model ) {
//
//        if(result.hasErrors()){
////            redirectAttributes.addFlashAttribute("message", "Nope");
//            return "home";
//        }
////        MultipartFile file = fileBucket.getFile();
//
////        fileIOService.importBoard(file);
//
////        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//        return HOME;
//    }
//
//}
//
//
//
