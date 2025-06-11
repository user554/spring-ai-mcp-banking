package com.example.bankingmcp.controller;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.ToolDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ToolListController {

    @Autowired
    private ToolCallbackProvider callbackProvider;

    @GetMapping("/tools")
    public String listTools(Model model) {
        List<ToolDefinition> tools = callbackProvider.getToolCallbacks().stream()
                .map(callback -> callback.getToolDefinition())
                .collect(Collectors.toList());
        model.addAttribute("tools", tools);
        return "tool-list";
    }
}